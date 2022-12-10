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

import io.fluentqa.qabox.excel.core.strategy.AutoWidthStrategy;
import io.fluentqa.qabox.excel.core.strategy.SheetStrategy;
import io.fluentqa.qabox.excel.core.strategy.WidthStrategy;
import io.fluentqa.qabox.excel.core.templatehandler.TemplateHandler;
import io.fluentqa.qabox.excel.exception.ExcelBuildException;
import io.fluentqa.qabox.excel.utils.ReflectUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.Map;

/**
 * excel创建者接口
 *
 * @author
 * @version 1.0
 */
public abstract class AbstractExcelBuilder implements ExcelBuilder {

    protected TemplateHandler templateHandler;

    protected HtmlToExcelFactory htmlToExcelFactory = new HtmlToExcelFactory();

    protected AbstractExcelBuilder(Class<? extends TemplateHandler> templateHandlerClass) {
        widthStrategy(WidthStrategy.COMPUTE_AUTO_WIDTH);
        sheetStrategy(SheetStrategy.MULTI_SHEET);
        this.templateHandler = ReflectUtil.newInstance(templateHandlerClass);
    }

    @Override
    public AbstractExcelBuilder workbookType(WorkbookType workbookType) {
        htmlToExcelFactory.workbookType(workbookType);
        return this;
    }

    @Override
    public AbstractExcelBuilder useDefaultStyle() {
        htmlToExcelFactory.useDefaultStyle();
        return this;
    }

    @Override
    public ExcelBuilder applyDefaultStyle() {
        htmlToExcelFactory.applyDefaultStyle();
        return this;
    }

    @Override
    public AbstractExcelBuilder widthStrategy(WidthStrategy widthStrategy) {
        htmlToExcelFactory.widthStrategy(widthStrategy);
        return this;
    }

    @Deprecated
    @Override
    public AbstractExcelBuilder autoWidthStrategy(AutoWidthStrategy autoWidthStrategy) {
        htmlToExcelFactory.widthStrategy(AutoWidthStrategy.map(autoWidthStrategy));
        return this;
    }

    @Override
    public AbstractExcelBuilder sheetStrategy(SheetStrategy sheetStrategy) {
        htmlToExcelFactory.sheetStrategy(sheetStrategy);
        return this;
    }

    @Override
    public AbstractExcelBuilder freezePanes(FreezePane... freezePanes) {
        if (freezePanes == null || freezePanes.length == 0) {
            return this;
        }
        htmlToExcelFactory.freezePanes(freezePanes);
        return this;
    }

    /**
     * 构建
     *
     * @param data 模板参数
     * @return Workbook
     */
    @Override
    public <T> Workbook build(Map<String, T> data) {
        String template = templateHandler.render(data);
        try {
            return HtmlToExcelFactory.readHtml(template, htmlToExcelFactory).build();
        } catch (Exception e) {
            throw new ExcelBuildException("Build excel failure", e);
        }
    }

    @Override
    public ExcelBuilder classpathTemplate(String path) {
        templateHandler.classpathTemplate(path);
        return this;
    }

    @Deprecated
    @Override
    public ExcelBuilder template(String path) {
        return classpathTemplate(path);
    }

    @Override
    public ExcelBuilder fileTemplate(String dirPath, String fileName) {
        templateHandler.fileTemplate(dirPath, fileName);
        return this;
    }

    @Override
    public void close() throws IOException {
        if (htmlToExcelFactory != null) {
            htmlToExcelFactory.closeWorkbook();
        }
    }
}
