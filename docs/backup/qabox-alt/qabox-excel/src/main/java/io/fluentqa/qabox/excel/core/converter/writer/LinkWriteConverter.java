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
import io.fluentqa.qabox.excel.core.constant.LinkEmail;
import io.fluentqa.qabox.excel.core.constant.LinkType;
import io.fluentqa.qabox.excel.core.constant.LinkUrl;
import io.fluentqa.qabox.excel.core.container.Pair;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;
import io.fluentqa.qabox.excel.core.converter.WriteConverter;

import java.lang.reflect.Field;

/**
 * 超链接写转换器
 *
 * @author
 * @version 1.0
 */
public class LinkWriteConverter implements WriteConverter {

    @Override
    public Pair<Class, Object> convert(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        ExcelColumnMapping mapping = convertContext.excelColumnMappingMap.get(field);
        LinkType linkType = mapping.linkType;
        switch (linkType) {
            case URL:
                return Pair.of(LinkUrl.class, fieldVal);
            case EMAIL:
                return Pair.of(LinkEmail.class, fieldVal);
            default:
                throw new IllegalArgumentException("Illegal linkType type, only URL or email");
        }
    }

    @Override
    public boolean support(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        ExcelColumnMapping mapping = convertContext.excelColumnMappingMap.get(field);
        return mapping != null && !LinkType.NONE.equals(mapping.linkType);
    }
}
