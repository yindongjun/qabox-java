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
package io.fluentqa.qabox.excel.core.converter.writer;

import io.fluentqa.qabox.excel.core.ExcelColumnMapping;
import io.fluentqa.qabox.excel.core.cache.WeakCache;
import io.fluentqa.qabox.excel.core.constant.Constants;
import io.fluentqa.qabox.excel.core.container.Pair;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;
import io.fluentqa.qabox.excel.core.converter.CustomWriteContext;
import io.fluentqa.qabox.excel.core.converter.DefaultCustomWriteConverter;
import io.fluentqa.qabox.excel.core.converter.WriteConverter;

import java.lang.reflect.Field;

/**
 * 自定义映射关系
 *
 * @author
 * @version 1.0
 */
public class CustomWriteConverter implements WriteConverter {

    private final WeakCache<Class, io.fluentqa.qabox.excel.core.converter.CustomWriteConverter> cache = new WeakCache<>();

    @Override
    public boolean support(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        ExcelColumnMapping mapping = convertContext.excelColumnMappingMap.get(field);
        return mapping != null && mapping.customWriteConverter != null && mapping.customWriteConverter != DefaultCustomWriteConverter.class;
    }

    @Override
    public Pair<Class, Object> convert(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        ExcelColumnMapping excelColumnMapping = convertContext.excelColumnMappingMap.get(field);
        Class<? extends io.fluentqa.qabox.excel.core.converter.CustomWriteConverter> converter = excelColumnMapping.customWriteConverter;
        // 构建上下文
        CustomWriteContext customWriteContext = new CustomWriteContext();
        customWriteContext.setField(field);
        // 尝试绑定上下文中是否存在
        Object target = convertContext.configuration.applicationBeans.get(converter);
        if (target != null) {
            Object result = ((io.fluentqa.qabox.excel.core.converter.CustomWriteConverter) target).convert(fieldVal, customWriteContext);
            return result == null ? Constants.NULL_PAIR : Pair.of(result.getClass(), result);
        }
        if (cache.get(converter) == null) {
            io.fluentqa.qabox.excel.core.converter.CustomWriteConverter customWriteConverter;
            try {
                customWriteConverter = converter.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            cache.cache(converter, customWriteConverter);
        }
        Object result = cache.get(converter).convert(fieldVal, customWriteContext);
        return result == null ? Constants.NULL_PAIR : Pair.of(result.getClass(), result);
    }
}
