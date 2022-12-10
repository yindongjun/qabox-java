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
package io.fluentqa.qabox.excel.core;

import io.fluentqa.qabox.excel.core.annotation.ExcelColumn;
import io.fluentqa.qabox.excel.core.annotation.Prompt;
import io.fluentqa.qabox.excel.core.constant.FileType;
import io.fluentqa.qabox.excel.core.constant.LinkType;
import io.fluentqa.qabox.excel.core.converter.CustomWriteConverter;
import io.fluentqa.qabox.excel.utils.StringUtil;

/**
 * @author
 * @version 1.0
 */
public final class ExcelColumnMapping {

    /**
     * 列标题
     */
    public String title;

    /**
     * 顺序，数值越大越靠后
     */
    public int order;

    /**
     * 列索引，从零开始，不允许重复
     */
    public int index;

    /**
     * 分组
     */
    public Class<?>[] groups;

    /**
     * 为null时默认值
     */
    public String defaultValue;

    /**
     * 宽度
     */
    public int width;

    /**
     * 是否强制转换成字符串
     */
    public boolean convertToString;

    /**
     * 格式化，时间、金额等
     */
    public String format;

    /**
     * 样式
     */
    public String[] style;

    /**
     * 链接
     */
    public LinkType linkType;

    /**
     * 简单映射，如"1:男,2:女"
     */
    public String mapping;
    /**
     * 自定义写转换器
     */
    public Class<? extends CustomWriteConverter> customWriteConverter;

    /**
     * 文件类型
     */
    public FileType fileType;

    /**
     * 是否为公式
     */
    public boolean formula;

    /**
     * 提示语
     */
    public PromptContainer promptContainer;

    public static ExcelColumnMapping mapping(ExcelColumn excelColumn) {
        ExcelColumnMapping result = new ExcelColumnMapping();
        result.title = excelColumn.title();
        result.order = excelColumn.order();
        result.index = excelColumn.index();
        result.groups = excelColumn.groups();
        result.defaultValue = excelColumn.defaultValue();
        result.width = excelColumn.width();
        result.convertToString = excelColumn.convertToString();
        if (!excelColumn.format().isEmpty()) {
            result.format = excelColumn.format();
        } else if (!excelColumn.dateFormatPattern().isEmpty()) {
            result.format = excelColumn.dateFormatPattern();
        } else if (!excelColumn.decimalFormat().isEmpty()) {
            result.format = excelColumn.decimalFormat();
        } else {
            result.format = "";
        }
        result.style = excelColumn.style();
        result.linkType = excelColumn.linkType();
        result.mapping = excelColumn.mapping();
        result.fileType = excelColumn.fileType();
        result.formula = excelColumn.formula();
        result.customWriteConverter = excelColumn.writeConverter();
        // 提示
        Prompt prompt = excelColumn.prompt();
        if (StringUtil.isNotBlank(prompt.text())) {
            PromptContainer promptContainer = new PromptContainer();
            promptContainer.title = prompt.title();
            promptContainer.text = prompt.text();
            result.promptContainer = promptContainer;
        }
        return result;
    }
}
