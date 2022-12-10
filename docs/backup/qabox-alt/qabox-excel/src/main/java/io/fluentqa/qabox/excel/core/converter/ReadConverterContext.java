
package io.fluentqa.qabox.excel.core.converter;

import io.fluentqa.qabox.excel.core.ExcelColumnMapping;
import io.fluentqa.qabox.excel.core.ReadContext;
import io.fluentqa.qabox.excel.core.annotation.MultiColumn;
import io.fluentqa.qabox.excel.core.cache.WeakCache;
import io.fluentqa.qabox.excel.core.converter.reader.BigDecimalReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.BoolReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.DateReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.LocalDateReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.LocalDateTimeReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.LocalTimeReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.NumberReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.StringReadConverter;
import io.fluentqa.qabox.excel.core.converter.reader.TimestampReadConverter;
import io.fluentqa.qabox.excel.exception.ExcelReadException;
import io.fluentqa.qabox.excel.exception.SaxReadException;
import io.fluentqa.qabox.excel.utils.PropertyUtil;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * 读取转换器上下文

 */
public class ReadConverterContext {

    private static final Map<Class<?>, ReadConverter<String, ?>> READ_CONVERTERS = new HashMap<>();

    private static final WeakCache<Field, Properties> MAPPING_CACHE = new WeakCache<>();

    private static final Properties EMPTY_PROPERTIES = new Properties();
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReadConverterContext.class);

    static {
        BoolReadConverter boolReadConverter = new BoolReadConverter();
        READ_CONVERTERS.put(Boolean.class, boolReadConverter);
        READ_CONVERTERS.put(boolean.class, boolReadConverter);

        READ_CONVERTERS.put(Date.class, new DateReadConverter());
        READ_CONVERTERS.put(LocalDate.class, new LocalDateReadConverter());
        READ_CONVERTERS.put(LocalDateTime.class, new LocalDateTimeReadConverter());
        READ_CONVERTERS.put(LocalTime.class, new LocalTimeReadConverter());

        NumberReadConverter<Double> doubleReadConverter = NumberReadConverter.of(Double::valueOf);
        READ_CONVERTERS.put(Double.class, doubleReadConverter);
        READ_CONVERTERS.put(double.class, doubleReadConverter);

        NumberReadConverter<Float> floatReadConverter = NumberReadConverter.of(Float::valueOf);
        READ_CONVERTERS.put(Float.class, floatReadConverter);
        READ_CONVERTERS.put(float.class, floatReadConverter);

        NumberReadConverter<Long> longReadConverter = NumberReadConverter.of(Long::valueOf, true);
        READ_CONVERTERS.put(Long.class, longReadConverter);
        READ_CONVERTERS.put(long.class, longReadConverter);

        NumberReadConverter<Integer> integerReadConverter = NumberReadConverter.of(Integer::valueOf, true);
        READ_CONVERTERS.put(Integer.class, integerReadConverter);
        READ_CONVERTERS.put(int.class, integerReadConverter);

        NumberReadConverter<Short> shortReadConverter = NumberReadConverter.of(Short::valueOf, true);
        READ_CONVERTERS.put(Short.class, shortReadConverter);
        READ_CONVERTERS.put(short.class, shortReadConverter);

        NumberReadConverter<Byte> byteReadConverter = NumberReadConverter.of(Byte::valueOf, true);
        READ_CONVERTERS.put(Byte.class, byteReadConverter);
        READ_CONVERTERS.put(byte.class, byteReadConverter);

        READ_CONVERTERS.put(BigDecimal.class, new BigDecimalReadConverter());
        READ_CONVERTERS.put(String.class, new StringReadConverter());

        READ_CONVERTERS.put(Timestamp.class, new TimestampReadConverter());

        NumberReadConverter<BigInteger> bigIntegerReadConverter = NumberReadConverter.of(BigInteger::new, true);
        READ_CONVERTERS.put(BigInteger.class, bigIntegerReadConverter);
    }

    public static boolean support(Class<?> clazz) {
        return READ_CONVERTERS.get(clazz) != null;
    }

    public synchronized ReadConverterContext registering(Class<?> clazz, ReadConverter<String, ?> readConverter) {
        READ_CONVERTERS.putIfAbsent(clazz, readConverter);
        return this;
    }

    @SuppressWarnings("unchecked")
    public static void convert(Object obj, ReadContext context, ConvertContext convertContext, BiFunction<Throwable, ReadContext, Boolean> exceptionFunction) {
        ReadConverter<String, ?> readConverter = READ_CONVERTERS.get(context.getField().getType());
        if (readConverter == null) {
            MultiColumn multiColumn = context.getField().getAnnotation(MultiColumn.class);
            if (multiColumn != null) {
                readConverter = READ_CONVERTERS.get(multiColumn.classType());
            }
            if (readConverter == null) {
                throw new IllegalStateException("No suitable type converter was found.");
            }
        }
        Object value = null;
        try {
            Properties properties = MAPPING_CACHE.get(context.getField());
            if (properties == null) {
                ExcelColumnMapping mapping = convertContext.excelColumnMappingMap.get(context.getField());
                if (mapping != null && !mapping.mapping.isEmpty()) {
                    properties = PropertyUtil.getReverseProperties(mapping);
                } else {
                    properties = EMPTY_PROPERTIES;
                }
                MAPPING_CACHE.cache(context.getField(), properties);
            }
            String mappingVal = properties.getProperty(context.getVal());
            if (mappingVal != null) {
                context.setVal(mappingVal);
            }
            value = readConverter.convert(context.getVal(), context.getField(), convertContext);
        } catch (Exception e) {
            Boolean toContinue = exceptionFunction.apply(e, context);
            if (!toContinue) {
                throw new ExcelReadException("Failed to convert content,field:[" + context.getField().getDeclaringClass().getName() + "#" + context.getField().getName() + "],content:[" + context.getVal() + "],rowNum:[" + context.getRowNum() + "]", e);
            }
        }
        if (value == null) {
            return;
        }
        try {
            if (obj instanceof List) {
                ((List) obj).add(value);
            } else {
                context.getField().set(obj, value);
            }
        } catch (Exception e) {
            throw new SaxReadException("Failed to set the " + context.getField().getDeclaringClass().getName() + "#" + context.getField().getName() + " field value to " + context.getVal(), e);
        }
    }
}
