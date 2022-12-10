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

import java.util.LinkedList;
import java.util.List;

/**
 * 工作簿元数据
 *
 * @author
 * @version 1.0
 */
public class WorkbookMetaData {

    private int sheetCount;

    private List<SheetMetaData> sheetMetaDataList = new LinkedList<>();

    public int getSheetCount() {
        return sheetCount;
    }

    public List<SheetMetaData> getSheetMetaDataList() {
        return sheetMetaDataList;
    }

    public void setSheetCount(int sheetCount) {
        this.sheetCount = sheetCount;
    }

    public void setSheetMetaDataList(List<SheetMetaData> sheetMetaDataList) {
        this.sheetMetaDataList = sheetMetaDataList;
    }
}
