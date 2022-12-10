package io.fluentqa.qabox.excel.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MultiColumn {
    /**
     * 列字段类型
     *
     * @return 类类型
     */
    Class<?> classType();
}
