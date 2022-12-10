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

import io.fluentqa.qabox.excel.core.annotation.MultiColumn;
import io.fluentqa.qabox.excel.core.constant.BooleanDropDownList;
import io.fluentqa.qabox.excel.core.constant.DropDownList;
import io.fluentqa.qabox.excel.core.constant.NumberDropDownList;
import io.fluentqa.qabox.excel.core.container.Pair;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;
import io.fluentqa.qabox.excel.core.converter.WriteConverter;
import io.fluentqa.qabox.excel.utils.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 下拉列表转换器
 *
 * @author
 * @version 1.0
 */
public class DropDownListWriteConverter implements WriteConverter {

    @Override
    public Pair<Class, Object> convert(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        String content = "";
        if (fieldType == List.class) {
            List<?> list = ((List<?>) fieldVal);
            content = list.stream().map(Object::toString).collect(Collectors.joining(","));
            // 确定数据类型
            Optional<?> optional = list.stream().filter(Objects::nonNull).findFirst();
            if (optional.isPresent()) {
                Class clazz = optional.get().getClass();
                if (ReflectUtil.isBool(clazz)) {
                    return Pair.of(BooleanDropDownList.class, content);
                }
                if (ReflectUtil.isNumber(clazz)) {
                    return Pair.of(NumberDropDownList.class, content);
                }
            }
        } else {
            int len = Array.getLength(fieldVal);
            if (len == 0) {
                return Pair.of(DropDownList.class, content);
            }
            Object[] obj = new Object[len];
            for (int i = 0; i < len; i++) {
                obj[i] = Array.get(fieldVal, i);
            }
            content = Stream.of(obj).map(Object::toString).collect(Collectors.joining(","));
            Class clazz = Array.get(fieldVal, 0).getClass();
            if (ReflectUtil.isBool(clazz)) {
                return Pair.of(BooleanDropDownList.class, content);
            }
            if (ReflectUtil.isNumber(clazz)) {
                return Pair.of(NumberDropDownList.class, content);
            }
        }
        return Pair.of(DropDownList.class, content);
    }

    @Override
    public boolean support(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        return field.getType().isArray() || (field.getType() == List.class && !field.isAnnotationPresent(MultiColumn.class)) || (field.getType() == List.class && fieldType == Array.class);
    }
}
