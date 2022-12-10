
package io.fluentqa.qabox.excel.core.constant;


import io.fluentqa.qabox.excel.core.container.Pair;

import javax.lang.model.type.NullType;

/**
 * 常量集合

 */
public class Constants {

    public static final String XLS = ".xls";

    public static final String XLSX = ".xlsx";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String ONE = "1";

    public static final String ZERO = "0";

    public static final String HTML_SUFFIX = ".html";

    public static final String HTTP = "http";

    public static final String DATA = "data";

    public static final String COMMA = ",";

    public static final String QUOTES = "\"";

    public static final String CSV = ".csv";

    public static final String COLON = ":";

    public static final String ARROW = "->";

    public static final String SPOT = ".";

    public static final String LEFT_BRACKET = "(";

    public static final String RIGHT_BRACKET = ")";

    public static final String EQUAL = "=";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_LOCAL_TIME_FORMAT = "HH:mm:ss";

    public static final Pair<Class, Object> NULL_PAIR = Pair.of(NullType.class, null);
}
