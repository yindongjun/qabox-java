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
import io.fluentqa.qabox.excel.core.constant.Constants;
import io.fluentqa.qabox.excel.core.constant.FileType;
import io.fluentqa.qabox.excel.core.constant.ImageFile;
import io.fluentqa.qabox.excel.core.container.Pair;
import io.fluentqa.qabox.excel.core.converter.ConvertContext;
import io.fluentqa.qabox.excel.core.converter.WriteConverter;
import io.fluentqa.qabox.excel.utils.ImageUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;

/**
 * 图片写转换器
 *
 * @author
 * @version 1.0
 */
public class ImageWriteConverter implements WriteConverter {

    @Override
    public Pair<Class, Object> convert(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        if (fieldVal instanceof String) {
            String path = (String) fieldVal;
            if (path.startsWith(Constants.HTTP)) {
                return Pair.of(ImageFile.class, ImageUtil.getImageFromNetByUrl(path));
            } else if (path.startsWith(Constants.DATA)) {
                return Pair.of(ImageFile.class, ImageUtil.generateImageFromBase64(path));
            } else {
                return Pair.of(ImageFile.class, new File(path));
            }
        }
        if (fieldVal instanceof Path) {
            return Pair.of(ImageFile.class, ((Path) fieldVal).toFile());
        }
        return Pair.of(ImageFile.class, fieldVal);
    }

    @Override
    public boolean support(Field field, Class<?> fieldType, Object fieldVal, ConvertContext convertContext) {
        if (fieldType != File.class && fieldType != InputStream.class && fieldType != String.class) {
            return false;
        }
        ExcelColumnMapping mapping = convertContext.excelColumnMappingMap.get(field);
        return mapping != null && mapping.fileType == FileType.IMAGE;
    }
}
