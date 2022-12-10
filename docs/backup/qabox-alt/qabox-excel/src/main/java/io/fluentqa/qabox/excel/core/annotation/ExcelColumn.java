package io.fluentqa.qabox.excel.core.annotation;


import io.fluentqa.qabox.excel.core.constant.FileType;
import io.fluentqa.qabox.excel.core.constant.LinkType;
import io.fluentqa.qabox.excel.core.converter.CustomWriteConverter;
import io.fluentqa.qabox.excel.core.converter.DefaultCustomWriteConverter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelColumn {
    /**
     * 列标题
     *
     * @return 标题
     */
    String title() default "";

    /**
     * 顺序，数值越大越靠后
     *
     * @return int
     */
    int order() default 0;

    /**
     * 列索引，从零开始，不允许重复
     *
     * @return int
     */
    int index() default -1;

    /**
     * 分组
     *
     * @return 分组类类型集合
     */
    Class<?>[] groups() default {};

    /**
     * 为null时默认值
     *
     * @return 默认值
     */
    String defaultValue() default "";

    /**
     * 宽度
     *
     * @return 宽度
     */
    int width() default -1;

    /**
     * 是否强制转换成字符串
     *
     * @return 是否强制转换成字符串
     */
    boolean convertToString() default false;

    /**
     * 小数格式化，
     * 已过期，请使用format代替
     *
     * @return 格式化
     */
    @Deprecated
    String decimalFormat() default "";

    /**
     * 时间格式化，如yyyy-MM-dd HH:mm:ss，
     * 已过期，请使用format代替
     *
     * @return 时间格式化
     */
    @Deprecated
    String dateFormatPattern() default "";

    /**
     * 格式化，时间、金额等
     *
     * @return 格式化
     */
    String format() default "";

    /**
     * 样式
     *
     * @return 样式集合
     */
    String[] style() default {};

    /**
     * 链接
     *
     * @return linkType
     */
    LinkType linkType() default LinkType.NONE;

    /**
     * 简单映射，如"1:男,2:女"
     *
     * @return String
     */
    String mapping() default "";

    /**
     * 写转化器
     *
     * @return 映射提供者
     */
    Class<? extends CustomWriteConverter> writeConverter() default DefaultCustomWriteConverter.class;

    /**
     * 文件类型
     *
     * @return 文件类型
     */
    FileType fileType() default FileType.NONE;

    /**
     * 是否为公式
     *
     * @return true/false
     */
    boolean formula() default false;

    /**
     * 提示语
     *
     * @return 提示语
     */
    Prompt prompt() default @Prompt();
}
