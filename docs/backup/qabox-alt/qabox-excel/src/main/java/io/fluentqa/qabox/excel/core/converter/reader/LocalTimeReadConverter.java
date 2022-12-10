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
package io.fluentqa.qabox.excel.core.converter.reader;

import io.fluentqa.qabox.excel.core.converter.ConvertContext;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime转化
 *
 * @author
 * @version 1.0
 */
public class LocalTimeReadConverter extends AbstractReadConverter<LocalTime> {

    @Override
    protected LocalTime doConvert(String v, Field field, ConvertContext convertContext) {
        DateTimeFormatter dateTimeFormatter = this.getDateFormatFormatter(field, convertContext);
        return LocalTime.parse(v, dateTimeFormatter);
    }
}
