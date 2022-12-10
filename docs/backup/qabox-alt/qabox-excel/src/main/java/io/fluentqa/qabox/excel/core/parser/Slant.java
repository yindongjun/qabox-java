
package io.fluentqa.qabox.excel.core.parser;

/**
 * 斜线
 */
public class Slant {
    /**
     * 线宽
     */
    public double lineWidth = 0.5;
    /**
     * 线的风格
     */
    public int lineStyle = 0;
    /**
     * 线的颜色
     */
    public String lineStyleColor = "#000000";

    public Slant() {
    }

    public Slant(int lineStyle, String lineWidth, String lineStyleColor) {
        this.lineWidth = Double.parseDouble(lineWidth);
        this.lineStyle = lineStyle;
        this.lineStyleColor = lineStyleColor;
    }
}
