package io.fluentqa.qabox.excel.core.parser;

import io.fluentqa.qabox.excel.core.constant.Constants;
import io.fluentqa.qabox.excel.core.style.FontStyle;
import io.fluentqa.qabox.excel.utils.StringUtil;
import io.fluentqa.qabox.excel.utils.StyleUtil;
import io.fluentqa.qabox.excel.utils.TdUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 单元格所有样式解析以及提供
 * <p>
 * title样式仅支持title
 * <p>
 * cell样式规则，继承关系：globalCommonStyle(globalEvenStyle)-globalCellStyle,
 * 如存在相同样式，子样式覆盖父样式
 *
 * @author
 * @version 1.0
 */
public final class StyleParser {
    /**
     * 全局标题样式
     */
    private Map<String, String> globalTitleStyle = Collections.emptyMap();
    /**
     * 全局单元格样式
     */
    private Map<String, String> globalCommonStyle = Collections.emptyMap();
    /**
     * 全局奇数行单元格样式
     */
    private Map<String, String> globalEvenStyle = Collections.emptyMap();
    /**
     * 全局单元格样式
     */
    private Map<String, String> globalCellStyle = Collections.emptyMap();
    /**
     * 全局超链接样式
     */
    private Map<String, String> globalLinkStyle = Collections.emptyMap();
    /**
     * 各个列样式
     */
    private Map<String, Map<String, String>> eachColumnStyle = new HashMap<>();
    /**
     * 格式样式Map
     */
    private Map<String, Map<String, String>> formatsStyleMap = new HashMap<>();
    /**
     * 自定义宽度
     */
    private Map<Integer, Integer> customWidthMap;
    /**
     * 是否为偶数行
     */
    private boolean isOddRow = true;
    /**
     * 无样式
     */
    private boolean noStyle;

    public StyleParser(Map<Integer, Integer> customWidthMap) {
        this.customWidthMap = customWidthMap;
    }

    /**
     * 解析全局样式，{@link io.fluentqa.qabox.excel.core.annotation.ExcelModel}
     *
     * @param styles 全局样式
     */
    public void parse(Set<String> styles) {
        if (noStyle) {
            return;
        }
        Map<String, String> styleMap = new HashMap<>();
        styles.forEach(style -> {
            String[] splits = style.split(Constants.ARROW);
            if (splits.length == 1) {
                styleMap.putIfAbsent("cell", "cell->" + style);
                return;
            }
            boolean appoint = splits[0].contains("&");
            if (appoint) {
                eachColumnStyle.put(splits[0], StyleUtil.parseStyle(splits[1]));
            } else {
                styleMap.putIfAbsent(splits[0], style);
            }
        });
        globalTitleStyle = parseStyle(styleMap, "title");
        globalCommonStyle = parseStyle(styleMap, "odd");
        globalEvenStyle = parseStyle(styleMap, "even");
        globalCellStyle = parseStyle(styleMap, "cell");

        String linkStyle = styleMap.get("link");
        if (linkStyle != null) {
            globalLinkStyle = StyleUtil.parseStyle(linkStyle.split(Constants.ARROW)[1]);
        } else {
            globalLinkStyle = new HashMap<>();
            globalLinkStyle.put(FontStyle.FONT_COLOR, "blue");
            globalLinkStyle.put(FontStyle.TEXT_DECORATION, FontStyle.UNDERLINE);
        }
    }

    private Map<String, String> parseStyle(Map<String, String> styleMap, String prefix) {
        String style = styleMap.get(prefix);
        return style == null ? Collections.emptyMap() : StyleUtil.parseStyle(style.split(Constants.ARROW)[1]);
    }

    public void setColumnStyle(Field field, int fieldIndex, String... columnStyles) {
        if (noStyle) {
            return;
        }
        setEachColumnStyle("title", fieldIndex, globalTitleStyle);
        setEachColumnStyle("even", fieldIndex, globalEvenStyle);
        setEachColumnStyle("odd", fieldIndex, globalCommonStyle);
        setEachColumnStyle("cell", fieldIndex, globalCellStyle);
        if (columnStyles == null) {
            return;
        }
        for (String columnStyle : columnStyles) {
            if (StringUtil.isBlank(columnStyle)) {
                throw new IllegalArgumentException("Illegal style,field:" + field.getName());
            }
            String[] splits = columnStyle.split(Constants.ARROW);
            if (splits.length == 1) {
                // 发现未设置样式归属，则设置为全局样式，清除其他样式
                setEachColumnStyle("cell", fieldIndex, StyleUtil.parseStyle(splits[0]));
            } else {
                setEachColumnStyle(splits[0], fieldIndex, StyleUtil.parseStyle(splits[1]));
            }
        }
    }

    private void setEachColumnStyle(String prefix, int fieldIndex, Map<String, String> styleMap) {
        if (styleMap == null || styleMap.isEmpty()) {
            return;
        }
        String stylePrefix = prefix + "&" + fieldIndex;
        Map<String, String> parentStyleMap = eachColumnStyle.get(stylePrefix);
        if (parentStyleMap == null || parentStyleMap.isEmpty()) {
            parentStyleMap = new HashMap<>(styleMap);
            eachColumnStyle.put(stylePrefix, parentStyleMap);
        } else {
            parentStyleMap.putAll(styleMap);
        }
        setWidth(fieldIndex, parentStyleMap);
    }

    public Map<String, String> getTitleStyle(String styleKey) {
        if (noStyle) {
            return Collections.emptyMap();
        }
        return eachColumnStyle.getOrDefault(styleKey, globalTitleStyle);
    }

    public void toggle() {
        isOddRow = !isOddRow;
    }

    public Map<String, String> getCellStyle(int fieldIndex, ContentTypeEnum contentType, String format) {
        Map<String, String> style = Collections.emptyMap();
        if (!noStyle) {
            style = eachColumnStyle.get((isOddRow ? "odd&" : "even&") + fieldIndex);
            Map<String, String> cellStyleMap = eachColumnStyle.get("cell&" + fieldIndex);
            if (cellStyleMap != null) {
                if (style == null || style.isEmpty()) {
                    style = cellStyleMap;
                } else {
                    style = new HashMap<>(style);
                    style.putAll(cellStyleMap);
                }
            }
            if (style == null && !globalCellStyle.isEmpty()) {
                style = globalCellStyle;
            }
            if (style == null) {
                style = isOddRow ? globalCommonStyle : globalEvenStyle;
            }
            if (ContentTypeEnum.isLink(contentType)) {
                style = new HashMap<>(style);
                style.putAll(globalLinkStyle);
            }
        }
        if (format != null) {
            style = this.getFormatStyle(format, fieldIndex, style);
        }
        return style;
    }

    private Map<String, String> getFormatStyle(String format, int fieldIndex, Map<String, String> style) {
        Map<String, String> formatStyle = formatsStyleMap.get(format + "_" + fieldIndex + "_" + (isOddRow ? "odd&" : "even&"));
        if (formatStyle == null) {
            formatStyle = new HashMap<>(style);
            formatStyle.put("format", format);
            formatsStyleMap.put(format + "_" + fieldIndex, formatStyle);
        }
        return formatStyle;
    }

    private void setWidth(int fieldIndex, Map<String, String> styleMap) {
        String width = styleMap.get("width");
        if (width != null) {
            customWidthMap.put(fieldIndex, TdUtil.getValue(width));
        }
    }

    public void setCustomWidthMap(Map<Integer, Integer> customWidthMap) {
        this.customWidthMap = customWidthMap;
    }

    public void setNoStyle(boolean noStyle) {
        this.noStyle = noStyle;
    }
}
