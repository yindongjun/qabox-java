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

import org.apache.poi.ss.util.CellAddress;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * sax处理
 *
 * @author
 * @version 1.0
 */
class XSSFSaxReadHandler<T> extends AbstractReadHandler<T> implements XSSFSheetXMLHandler.SheetContentsHandler {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(XSSFSaxReadHandler.class);
    private int count;

    public XSSFSaxReadHandler(
            List<T> result,
            SaxExcelReader.ReadConfig<T> readConfig, Map<CellAddress, CellAddress> mergeCellMapping) {
        super(false, result, readConfig, mergeCellMapping);
    }

    @Override
    public void startRow(int rowNum, boolean newInstance) {
        newRow(rowNum, newInstance);
    }

    @Override
    public void endRow(int rowNum) {
        handleResult();
        count++;
    }

    @Override
    public void cell(CellAddress cellAddress, String formattedValue) {
        int thisCol = cellAddress.getColumn();
        handleField(thisCol, formattedValue);
    }

    @Override
    public void endSheet() {
        log.info("Import completed, total number of rows {}.", count);
    }
}
