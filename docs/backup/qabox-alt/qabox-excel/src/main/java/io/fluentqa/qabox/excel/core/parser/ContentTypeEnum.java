package io.fluentqa.qabox.excel.core.parser;

/**
 * 内容类型枚举
 */
public enum ContentTypeEnum {

    STRING,

    BOOLEAN,

    DOUBLE,

    DATE,

    DROP_DOWN_LIST,

    NUMBER_DROP_DOWN_LIST,

    BOOLEAN_DROP_DOWN_LIST,

    LINK_EMAIL,

    LINK_URL,

    IMAGE;

    public static boolean isString(ContentTypeEnum contentTypeEnum) {
        return STRING == contentTypeEnum;
    }

    public static boolean isBool(ContentTypeEnum contentTypeEnum) {
        return BOOLEAN == contentTypeEnum;
    }

    public static boolean isDouble(ContentTypeEnum contentTypeEnum) {
        return DOUBLE == contentTypeEnum;
    }

    public static boolean isLink(ContentTypeEnum contentTypeEnum) {
        return LINK_URL == contentTypeEnum || LINK_EMAIL == contentTypeEnum;
    }

    public static boolean isDropdownList(ContentTypeEnum contentTypeEnum) {
        return DROP_DOWN_LIST == contentTypeEnum || NUMBER_DROP_DOWN_LIST == contentTypeEnum || BOOLEAN_DROP_DOWN_LIST == contentTypeEnum;
    }

}
