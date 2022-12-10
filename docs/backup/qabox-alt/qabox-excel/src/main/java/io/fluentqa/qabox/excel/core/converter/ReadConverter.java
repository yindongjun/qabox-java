
package io.fluentqa.qabox.excel.core.converter;

import java.lang.reflect.Field;

/**
 * 转换接口
 *

 */
public interface ReadConverter<E, T> {

    /**
     * 转换
     *
     * @param obj            被转换对象
     * @param field          字段，提供额外信息
     * @param convertContext 转换上下文
     * @return 转换结果
     */
    T convert(E obj, Field field, ConvertContext convertContext);
}
