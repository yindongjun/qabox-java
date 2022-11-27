package io.fluentqa.qabox.mindmapping;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
public @interface MindMappingLevel {
  int value();
}
