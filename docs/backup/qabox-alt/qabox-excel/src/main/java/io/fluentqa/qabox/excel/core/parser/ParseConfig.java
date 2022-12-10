
package io.fluentqa.qabox.excel.core.parser;

import io.fluentqa.qabox.excel.core.strategy.WidthStrategy;

/**
 * 解析配置

 */
public class ParseConfig {

    private WidthStrategy widthStrategy;

    private boolean isComputeAutoWidth;

    public ParseConfig(WidthStrategy widthStrategy) {
        this.widthStrategy = widthStrategy;
        this.isComputeAutoWidth = WidthStrategy.isComputeAutoWidth(widthStrategy);
    }

    public WidthStrategy getWidthStrategy() {
        return this.widthStrategy;
    }

    public boolean isComputeAutoWidth() {
        return this.isComputeAutoWidth;
    }

    public void setWidthStrategy(WidthStrategy widthStrategy) {
        this.widthStrategy = widthStrategy;
    }

    public void setComputeAutoWidth(boolean isComputeAutoWidth) {
        this.isComputeAutoWidth = isComputeAutoWidth;
    }
}
