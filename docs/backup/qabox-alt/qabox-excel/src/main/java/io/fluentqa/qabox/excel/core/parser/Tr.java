package io.fluentqa.qabox.excel.core.parser;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Tr {

    /**
     * 索引
     */
    public int index;
    /**
     * 行单元格
     */
    public List<Td> tdList = Collections.emptyList();
    /**
     * 最大宽度
     */
    public Map<Integer, Integer> colWidthMap;
    /**
     * 是否可见
     */
    public boolean visibility = true;
    /**
     * 行高度
     */
    public int height;
    /**
     * 是否来源于模板
     */
    public boolean fromTemplate;

    public Tr(int index, int height) {
        this.index = index;
        this.height = height;
    }

    public Tr(int index, int height, boolean fromTemplate) {
        this.index = index;
        this.height = height;
        this.fromTemplate = fromTemplate;
    }
}
