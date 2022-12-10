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

import io.fluentqa.qabox.excel.core.container.Pair;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime写转换器
 *
 * @author
 * @version 1.0
 */
public class LocalTimeWriteConverter extends DateTimeWriteConverter {

    @Override
    public boolean support(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        return LocalTime.class == fieldType;
    }

    @Override
    public Pair<Class, Object> convert(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        String dateFormatPattern = getDateFormatPattern(convertContext, field, fieldType);
        DateTimeFormatter formatter = getDateTimeFormatter(dateFormatPattern);
        return Pair.of(String.class, formatter.format((LocalTime) fieldVal));
    }
}
