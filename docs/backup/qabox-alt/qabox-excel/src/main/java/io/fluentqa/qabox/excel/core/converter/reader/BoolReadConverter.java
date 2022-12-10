package io.fluentqa.qabox.excel.core.converter.reader;

import io.fluentqa.qabox.excel.core.constant.Constants;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;

import java.lang.reflect.Field;
import java.util.Objects;


public class BoolReadConverter extends AbstractReadConverter<Boolean> {

    @Override
    public Boolean doConvert(String v, Field field, ConvertContext convertContext) {
        if (Objects.equals(Constants.ONE, v) || v.equalsIgnoreCase(Constants.TRUE)) {
            return Boolean.TRUE;
        }
        if (Objects.equals(Constants.ZERO, v) || v.equalsIgnoreCase(Constants.FALSE)) {
            return Boolean.FALSE;
        }
        throw new IllegalStateException("Cell content does not match the type of field to be injected,field is " + field.getName() + ",value is \"" + v + "\"");
    }
}
