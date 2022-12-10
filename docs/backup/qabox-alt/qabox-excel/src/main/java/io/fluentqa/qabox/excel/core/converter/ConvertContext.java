
package io.fluentqa.qabox.excel.core.converter;

import io.fluentqa.qabox.excel.core.Configuration;
import io.fluentqa.qabox.excel.core.ExcelColumnMapping;
import io.fluentqa.qabox.excel.core.constant.AllConverter;
import io.fluentqa.qabox.excel.core.constant.CsvConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ConvertContext {
    /**
     *
     */
    public Configuration configuration = new Configuration();

    /**
     *
     */
    public Map<Field, ExcelColumnMapping> excelColumnMappingMap = new HashMap<>();
    /**
     * csv or excel
     */
    public Class converterType;

    public boolean isConvertCsv;

    public ConvertContext(boolean isConvertCsv) {
        this.isConvertCsv = isConvertCsv;
        this.converterType = isConvertCsv ? CsvConverter.class : AllConverter.class;
    }
}
