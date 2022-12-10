
package io.fluentqa.qabox.excel.core.converter;

import io.fluentqa.qabox.excel.core.cache.WeakCache;
import io.fluentqa.qabox.excel.core.constant.AllConverter;
import io.fluentqa.qabox.excel.core.constant.Constants;
import io.fluentqa.qabox.excel.core.constant.CsvConverter;
import io.fluentqa.qabox.excel.core.container.Pair;
import io.fluentqa.qabox.excel.core.converter.writer.BigDecimalWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.CustomWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.DateTimeWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.DropDownListWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.ImageWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.LinkWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.LocalTimeWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.MappingWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.MultiWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.OriginalWriteConverter;
import io.fluentqa.qabox.excel.core.converter.writer.StringWriteConverter;
import io.fluentqa.qabox.excel.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WriteConverterContext {

    private static final List<Pair<Class, WriteConverter>> WRITE_CONVERTER_CONTAINER = new ArrayList<>();

    private static final WeakCache<Pair<Field, Class<?>>, WriteConverter> EXCEL_CONVERTER_CACHE = new WeakCache<>();

    private static final WeakCache<Pair<Field, Class<?>>, WriteConverter> CSV_CONVERTER_CACHE = new WeakCache<>();

    private static final OriginalWriteConverter ORIGINAL_WRITE_CONVERTER = new OriginalWriteConverter();

    static {
        WRITE_CONVERTER_CONTAINER.add(Pair.of(CsvConverter.class, new DateTimeWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new LocalTimeWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new StringWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new BigDecimalWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new DropDownListWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new LinkWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new CustomWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new MappingWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new ImageWriteConverter()));
        WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, new MultiWriteConverter(WRITE_CONVERTER_CONTAINER)));
    }

    public static synchronized void registering(WriteConverter... writeConverters) {
        Objects.requireNonNull(writeConverters);
        for (WriteConverter writeConverter : writeConverters) {
            WRITE_CONVERTER_CONTAINER.add(Pair.of(AllConverter.class, writeConverter));
        }
    }

    public static Pair<? extends Class, Object> convert(Field field, Object object, ConvertContext convertContext) {
        Object result = ReflectUtil.getFieldValue(object, field);
        if (result == null) {
            return Constants.NULL_PAIR;
        }
        WriteConverter writeConverter = getWriteConverter(field, field.getType(), result, convertContext, WRITE_CONVERTER_CONTAINER);
        return writeConverter.convert(field, field.getType(), result, convertContext);
    }

    public static WriteConverter getWriteConverter(Field field, Class<?> fieldType, Object result, ConvertContext convertContext, List<Pair<Class, WriteConverter>> writeConverterContainer) {
        WriteConverter writeConverter = convertContext.isConvertCsv ? CSV_CONVERTER_CACHE.get(Pair.of(field, fieldType)) : EXCEL_CONVERTER_CACHE.get(Pair.of(field, fieldType));
        if (writeConverter != null) {
            return writeConverter;
        }
        Optional<WriteConverter> writeConverterOptional = writeConverterContainer.stream()
                .filter(pair -> (pair.getKey() == convertContext.converterType || pair.getKey() == AllConverter.class) && pair.getValue().support(field, fieldType, result, convertContext))
                .map(Pair::getValue)
                .findFirst();
        writeConverter = writeConverterOptional.orElse(ORIGINAL_WRITE_CONVERTER);
        if (convertContext.isConvertCsv) {
            CSV_CONVERTER_CACHE.cache(Pair.of(field, fieldType), writeConverter);
        } else {
            EXCEL_CONVERTER_CACHE.cache(Pair.of(field, fieldType), writeConverter);
        }
        return writeConverter;
    }
}
