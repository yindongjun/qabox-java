package yi.master.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义查询（高级查询）通过此注解表明各个查询字段的查询设置
 * @author xuwangcheng
 * @date 20191027
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomConditionSetting {
	public static final String DATETIME_TYPE = "datetime";
	public static final String STRING_TYPE = "string";

    /**
     * 条件类型，目前支持string、datetime
     * @return
     */
	String conditionType() default STRING_TYPE;

    /**
     * 运算符，目前仅支持 ‘like’ 和 ‘=’ '>' '<'
     * @return
     */
	String operator() default "like";
}
