/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fluentqa.qabox.excel.core;

import io.fluentqa.qabox.excel.core.strategy.SheetStrategy;
import io.fluentqa.qabox.excel.core.strategy.WidthStrategy;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author
 * @version 1.0
 */
public interface ExcelFactory {

    /**
     * 是否使用默认样式
     *
     * @return ExcelFactory
     */
    ExcelFactory useDefaultStyle();

    /**
     * 应用默认样式
     *
     * @return ExcelFactory
     */
    ExcelFactory applyDefaultStyle();

    /**
     * 窗口冻结
     *
     * @param freezePanes 窗口冻结区域
     * @return ExcelFactory
     */
    ExcelFactory freezePanes(FreezePane... freezePanes);

    /**
     * 设置workbook类型
     *
     * @param workbookType 工作簿类型
     * @return ExcelFactory
     */
    ExcelFactory workbookType(WorkbookType workbookType);

    /**
     * 自动宽度策略
     *
     * @param widthStrategy 策略
     * @return ExcelFactory
     */
    ExcelFactory widthStrategy(WidthStrategy widthStrategy);

    /**
     * 生成 sheet 策略
     *
     * @param sheetStrategy 策略
     * @return ExcelFactory
     */
    ExcelFactory sheetStrategy(SheetStrategy sheetStrategy);

    /**
     * 构建
     *
     * @return workbook
     */
    Workbook build();
}
