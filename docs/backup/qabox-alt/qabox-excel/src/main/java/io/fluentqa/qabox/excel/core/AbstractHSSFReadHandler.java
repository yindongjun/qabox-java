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

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象HSSF读取处理器
 *
 * @author
 * @version 1.0
 */
abstract class AbstractHSSFReadHandler implements HSSFListener {

    protected BoundSheetRecord[] orderedBSRs;

    protected final List<BoundSheetRecord> boundSheetRecords = new ArrayList<>();

    protected int sheetIndex = -1;

    protected String sheetName;

    protected SaxExcelReader.ReadConfig<?> readConfig;

    protected POIFSFileSystem fs;

    protected void process() throws IOException {
        HSSFRequest request = new HSSFRequest();
        request.addListenerForAllRecords(new EventWorkbookBuilder.SheetRecordCollectingListener(this));
        new HSSFEventFactory().processWorkbookEvents(request, fs);
    }
}
