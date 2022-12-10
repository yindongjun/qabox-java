package io.fluentqa.qabox.excel.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Prompt {

    /**
     * 标题
     *
     * @return 提示语
     */
    String title() default "";

    /**
     * 提示
     *
     * @return 提示
     */
    String text() default "";
}
