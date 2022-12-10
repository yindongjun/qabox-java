
package io.fluentqa.qabox.excel.core.converter;


import io.fluentqa.qabox.excel.core.container.Pair;

import java.lang.reflect.Field;

public interface WriteConverter {

    /**
     * 转换
     *
     * @param field          字段
     * @param fieldType      字段类型
     * @param fieldVal       字段对应的值
     * @param convertContext 转换上下文
     * @return T
     */
    Pair<Class, Object> convert(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext);

    /**
     * 是否支持转换
     *
     * @param field          字段
     * @param fieldType      字段类型
     * @param fieldVal       字段值
     * @param convertContext 转换上下文
     * @return true/false
     */
    boolean support(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext);

}
