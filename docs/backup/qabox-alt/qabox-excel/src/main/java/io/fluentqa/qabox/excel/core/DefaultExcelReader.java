/*
 * Copyright 2019 liaochong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fluentqa.qabox.excel.core;

import io.fluentqa.qabox.excel.core.annotation.ExcelColumn;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;
import io.fluentqa.qabox.excel.core.converter.ReadConverterContext;
import io.fluentqa.qabox.excel.core.reflect.ClassFieldContainer;
import io.fluentqa.qabox.excel.exception.ExcelReadException;
import io.fluentqa.qabox.excel.utils.ConfigurationUtil;
import io.fluentqa.qabox.excel.utils.FieldDefinition;
import io.fluentqa.qabox.excel.utils.ReflectUtil;
import io.fluentqa.qabox.excel.utils.StringUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFAnchor;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author
 * @version 1.0
 */
public class DefaultExcelReader<T> {

    private static final Logger log = LoggerFactory.getLogger(DefaultExcelReader.class);

    private static final int DEFAULT_SHEET_INDEX = 0;

    private final Class<T> dataType;

    private int sheetIndex = DEFAULT_SHEET_INDEX;

    private Predicate<Row> rowFilter = row -> true;

    private Predicate<T> beanFilter = bean -> true;

    private Workbook wb;

    private BiFunction<Throwable, ReadContext, Boolean> exceptionFunction = (e, c) -> false;

    private final ReadContext<T> context = new ReadContext<>();

    private Map<String, XSSFPicture> xssfPicturesMap = Collections.emptyMap();

    private Map<String, HSSFPicture> hssfPictureMap = Collections.emptyMap();

    private boolean isXSSFSheet;

    private String sheetName;

    private final ConvertContext convertContext = new ConvertContext(false);

    private Function<String, String> trim = v -> {
        if (v == null) {
            return v;
        }
        return v.trim();
    };

    /**
     * sheet前置处理函数
     */
    private Consumer<Sheet> startSheetConsumer = sheet -> {
    };

    private DefaultExcelReader(Class<T> dataType) {
        this.dataType = dataType;
        // 全局配置获取
        if (dataType != Map.class) {
            ClassFieldContainer classFieldContainer = ReflectUtil.getAllFieldsOfClass(dataType);
            ConfigurationUtil.parseConfiguration(classFieldContainer, convertContext.configuration);

            List<Field> fields = classFieldContainer.getFieldsByAnnotation(ExcelColumn.class);
            fields.forEach(field -> {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                if (excelColumn == null) {
                    return;
                }
                ExcelColumnMapping mapping = ExcelColumnMapping.mapping(excelColumn);
                convertContext.excelColumnMappingMap.put(field, mapping);
            });
        }
    }

    public static <T> DefaultExcelReader<T> of(Class<T> clazz) {
        return new DefaultExcelReader<>(clazz);
    }

    public DefaultExcelReader<T> sheet(int index) {
        if (index >= 0) {
            this.sheetIndex = index;
        } else {
            throw new IllegalArgumentException("Sheet index must be greater than or equal to 0");
        }
        return this;
    }

    public DefaultExcelReader<T> sheet(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public DefaultExcelReader<T> rowFilter(Predicate<Row> rowFilter) {
        this.rowFilter = rowFilter;
        return this;
    }

    public DefaultExcelReader<T> beanFilter(Predicate<T> beanFilter) {
        this.beanFilter = beanFilter;
        return this;
    }

    public DefaultExcelReader<T> exceptionally(BiFunction<Throwable, ReadContext, Boolean> exceptionFunction) {
        this.exceptionFunction = exceptionFunction;
        return this;
    }

    public DefaultExcelReader<T> noTrim() {
        this.trim = v -> v;
        return this;
    }

    public DefaultExcelReader<T> startSheet(Consumer<Sheet> startSheetConsumer) {
        this.startSheetConsumer = startSheetConsumer;
        return this;
    }

    public List<T> read(InputStream fileInputStream) {
        return this.read(fileInputStream, null);
    }

    public List<T> read(InputStream fileInputStream, String password) {
        return this.doRead(() -> getSheetOfInputStream(fileInputStream, password));
    }

    public List<T> read(File file) {
        return this.read(file, null);
    }

    public List<T> read(File file, String password) {
        return this.doRead(() -> getSheetOfFile(file, password));
    }

    private List<T> doRead(Supplier<Sheet> sheetSupplier) {
        Map<Integer, FieldDefinition> fieldDefinitionMap = ReflectUtil.getFieldDefinitionMapOfExcelColumn(dataType);
        if (fieldDefinitionMap.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            Sheet sheet = sheetSupplier.get();
            this.startSheetConsumer.accept(sheet);
            return getDataFromFile(sheet, fieldDefinitionMap);
        } finally {
            clearWorkbook();
        }
    }

    public void readThen(InputStream fileInputStream, Consumer<T> consumer) {
        readThen(fileInputStream, null, consumer);
    }

    public void readThen(InputStream fileInputStream, String password, Consumer<T> consumer) {
        this.doReadThen(() -> getSheetOfInputStream(fileInputStream, password), consumer, null);
    }

    public void readThen(File file, Consumer<T> consumer) {
        readThen(file, null, consumer);
    }

    public void readThen(File file, String password, Consumer<T> consumer) {
        this.doReadThen(() -> getSheetOfFile(file, password), consumer, null);
    }

    public void readThen(InputStream fileInputStream, Function<T, Boolean> function) {
        readThen(fileInputStream, null, function);
    }

    public void readThen(InputStream fileInputStream, String password, Function<T, Boolean> function) {
        this.doReadThen(() -> getSheetOfInputStream(fileInputStream, password), null, function);
    }

    public void readThen(File file, Function<T, Boolean> function) {
        readThen(file, null, function);
    }

    public void readThen(File file, String password, Function<T, Boolean> function) {
        this.doReadThen(() -> getSheetOfFile(file, password), null, function);
    }

    private void doReadThen(Supplier<Sheet> sheetSupplier, Consumer<T> consumer, Function<T, Boolean> function) {
        Map<Integer, FieldDefinition> fieldDefinitionMap = ReflectUtil.getFieldDefinitionMapOfExcelColumn(dataType);
        if (fieldDefinitionMap.isEmpty()) {
            return;
        }
        try {
            Sheet sheet = sheetSupplier.get();
            readThenConsume(sheet, fieldDefinitionMap, consumer, function);
        } finally {
            clearWorkbook();
        }
    }

    private void clearWorkbook() {
        if (Objects.nonNull(wb)) {
            try {
                wb.close();
            } catch (IOException e) {
                throw new ExcelReadException("Close workbook failure", e);
            }
        }
    }

    private Sheet getSheetOfInputStream(InputStream fileInputStream, String password) {
        try {
            if (StringUtil.isBlank(password)) {
                wb = WorkbookFactory.create(fileInputStream);
            } else {
                wb = WorkbookFactory.create(fileInputStream, password);
            }
        } catch (IOException | EncryptedDocumentException e) {
            throw new ExcelReadException("Get sheet of excel failure", e);
        }
        return getSheet();
    }

    private Sheet getSheetOfFile(File file, String password) {
        try {
            if (StringUtil.isBlank(password)) {
                wb = WorkbookFactory.create(file);
            } else {
                wb = WorkbookFactory.create(file, password);
            }
        } catch (IOException | EncryptedDocumentException e) {
            throw new ExcelReadException("Get sheet of excel failure", e);
        }
        return getSheet();
    }

    private Sheet getSheet() {
        Sheet sheet;
        if (sheetName != null) {
            sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                throw new ExcelReadException("Cannot find sheet based on sheetName:" + sheetName);
            }
        } else {
            sheet = wb.getSheetAt(sheetIndex);
        }
        getAllPictures(sheet);
        return sheet;
    }

    private List<T> getDataFromFile(Sheet sheet, Map<Integer, FieldDefinition> fieldDefinitionMap) {
        long startTime = System.currentTimeMillis();
        final int firstRowNum = sheet.getFirstRowNum();
        final int lastRowNum = sheet.getLastRowNum();
        log.info("FirstRowNum:{},LastRowNum:{}", firstRowNum, lastRowNum);
        if (lastRowNum < 0) {
            log.info("Reading excel takes {} milliseconds", System.currentTimeMillis() - startTime);
            return Collections.emptyList();
        }
        DataFormatter formatter = new DataFormatter();
        List<T> result = new LinkedList<>();
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                log.info("Row of {} is null,it will be ignored.", i);
                continue;
            }
            boolean noMatchResult = rowFilter.negate().test(row);
            if (noMatchResult) {
                log.info("Row of {} does not meet the filtering criteria, it will be ignored.", i);
                continue;
            }
            int lastColNum = row.getLastCellNum();
            if (lastColNum < 0) {
                continue;
            }
            T obj = instanceObj(fieldDefinitionMap, formatter, row);
            if (beanFilter.test(obj)) {
                result.add(obj);
            }
        }
        log.info("Reading excel takes {} milliseconds", System.currentTimeMillis() - startTime);
        return result;
    }

    private void readThenConsume(Sheet sheet, Map<Integer, FieldDefinition> fieldDefinitionMap, Consumer<T> consumer, Function<T, Boolean> function) {
        long startTime = System.currentTimeMillis();
        final int firstRowNum = sheet.getFirstRowNum();
        final int lastRowNum = sheet.getLastRowNum();
        log.info("FirstRowNum:{},LastRowNum:{}", firstRowNum, lastRowNum);
        this.startSheetConsumer.accept(sheet);
        if (lastRowNum < 0) {
            log.info("Reading excel takes {} milliseconds", System.currentTimeMillis() - startTime);
            return;
        }
        DataFormatter formatter = new DataFormatter();
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                log.info("Row of {} is null,it will be ignored.", i);
                continue;
            }
            boolean noMatchResult = rowFilter.negate().test(row);
            if (noMatchResult) {
                log.info("Row of {} does not meet the filtering criteria, it will be ignored.", i);
                continue;
            }
            int lastColNum = row.getLastCellNum();
            if (lastColNum < 0) {
                continue;
            }
            T obj = instanceObj(fieldDefinitionMap, formatter, row);
            if (beanFilter.test(obj)) {
                if (consumer != null) {
                    consumer.accept(obj);
                } else if (function != null) {
                    Boolean noStop = function.apply(obj);
                    if (!noStop) {
                        break;
                    }
                }
            }
        }
        log.info("Reading excel takes {} milliseconds", System.currentTimeMillis() - startTime);
    }

    private void getAllPictures(Sheet sheet) {
        if (sheet instanceof XSSFSheet) {
            isXSSFSheet = true;
            XSSFDrawing xssfDrawing = ((XSSFSheet) sheet).getDrawingPatriarch();
            if (xssfDrawing == null) {
                return;
            }
            xssfPicturesMap = xssfDrawing.getShapes()
                    .stream()
                    .filter(s -> s instanceof XSSFPicture)
                    .map(s -> (XSSFPicture) s)
                    .collect(Collectors.toMap(s -> {
                        XSSFClientAnchor anchor = (XSSFClientAnchor) s.getAnchor();
                        return anchor.getRow1() + "_" + anchor.getCol1();
                    }, s -> s));
        } else if (sheet instanceof HSSFSheet) {
            HSSFPatriarch hssfPatriarch = ((HSSFSheet) sheet).getDrawingPatriarch();
            if (hssfPatriarch == null) {
                return;
            }
            Spliterator<HSSFShape> spliterator = hssfPatriarch.spliterator();
            hssfPictureMap = new HashMap<>();
            spliterator.forEachRemaining(shape -> {
                if (shape instanceof HSSFPicture) {
                    HSSFPicture picture = (HSSFPicture) shape;
                    HSSFAnchor anchor = picture.getAnchor();
                    if (anchor instanceof HSSFClientAnchor) {
                        int row = ((HSSFClientAnchor) anchor).getRow1();
                        int col = ((HSSFClientAnchor) anchor).getCol1();
                        hssfPictureMap.put(row + "_" + col, picture);
                    }
                }
            });
        }
    }

    private T instanceObj(Map<Integer, FieldDefinition> fieldDefinitionMap, DataFormatter formatter, Row row) {
        T obj = ReflectUtil.newInstance(dataType);
        fieldDefinitionMap.forEach((index, fieldDefinition) -> {
            if (fieldDefinition.getField().getType() == InputStream.class) {
                convertPicture(row, obj, index, fieldDefinition.getField());
                return;
            }
            Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell == null) {
                return;
            }
            String content = formatter.formatCellValue(cell);
            if (content == null) {
                return;
            }
            content = trim.apply(content);
            context.reset(obj, fieldDefinition.getField(), content, row.getRowNum(), index);
            ReadConverterContext.convert(obj, context, convertContext, exceptionFunction);
        });
        return obj;
    }

    private void convertPicture(Row row, T obj, Integer index, Field field) {
        byte[] pictureData;
        if (isXSSFSheet) {
            XSSFPicture xssfPicture = xssfPicturesMap.get(row.getRowNum() + "_" + index);
            if (xssfPicture == null) {
                return;
            }
            pictureData = xssfPicture.getPictureData().getData();
        } else {
            HSSFPicture hssfPicture = hssfPictureMap.get(row.getRowNum() + "_" + index);
            if (hssfPicture == null) {
                return;
            }
            pictureData = hssfPicture.getPictureData().getData();
        }
        try {
            field.set(obj, new ByteArrayInputStream(pictureData));
        } catch (IllegalAccessException e) {
            throw new ExcelReadException("Failed to read picture.", e);
        }
    }
}
