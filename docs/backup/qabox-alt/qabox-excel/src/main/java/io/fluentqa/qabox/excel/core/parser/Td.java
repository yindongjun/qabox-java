package io.fluentqa.qabox.excel.core.parser;

import io.fluentqa.qabox.excel.core.PromptContainer;
import io.fluentqa.qabox.excel.utils.TdUtil;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Td {
    /**
     * 所在行
     */
    public int row;
    /**
     * 所在列
     */
    public int col;
    /**
     * 跨行数
     */
    public int rowSpan;
    /**
     * 跨列数
     */
    public int colSpan;
    /**
     * 内容
     */
    public String content;
    /**
     * 内容类型
     */
    public ContentTypeEnum tdContentType = ContentTypeEnum.STRING;
    /**
     * 是否为th
     */
    public boolean th;
    /**
     * 单元格样式
     */
    public Map<String, String> style = Collections.emptyMap();
    /**
     * 公式
     */
    public boolean formula;
    /**
     * 链接
     */
    public String link;
    /**
     * 文件
     */
    public File file;
    /**
     * 文件流
     */
    public InputStream fileIs;
    /**
     * 格式化
     */
    public String format;

    /**
     * 时间是常用对象，特殊化
     */
    public Date date;

    public LocalDate localDate;

    public LocalDateTime localDateTime;

    public List<Font> fonts;

    public PromptContainer promptContainer;
    /**
     * 斜线
     */
    public Slant slant;
    /**
     * 批注
     */
    public Comment comment;

    public Td(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRowSpan(int rowSpan) {
        if (rowSpan < 2) {
            return;
        }
        this.rowSpan = rowSpan;
    }

    public void setColSpan(int colSpan) {
        if (colSpan < 2) {
            return;
        }
        this.colSpan = colSpan;
    }

    public int getRowBound() {
        return TdUtil.get(this.rowSpan, this.row);
    }

    public int getColBound() {
        return TdUtil.get(this.colSpan, this.col);
    }
}
