package io.fluentqa.qabox.excel.core.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * 富文本字体
 */
public class Font {

    /**
     * 整体字符串中起始位置
     */
    public int startIndex;

    /**
     * 整体字符串中终点位置，不包含
     */
    public int endIndex;

    /**
     * 单元格样式
     */
    public Map<String, String> style = new HashMap<>();
}
