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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date读取转换器
 *
 * @author
 * @version 1.0
 */
public class DateReadConverter extends AbstractReadConverter<Date> {

    @Override
    public Date doConvert(String v, Field field, ConvertContext convertContext) {
        if (isDateNumber(v)) {
            final long time = Long.parseLong(v);
            return new Date(time);
        }
        if (isDateDecimalNumber(v)) {
            final long time = convertExcelNumberDateToMilli(v);
            return new Date(time);
        }
        String dateFormatPattern = getDateFormatPattern(field, convertContext);
        SimpleDateFormat sdf = this.getSimpleDateFormat(dateFormatPattern);
        try {
            return sdf.parse(v);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
