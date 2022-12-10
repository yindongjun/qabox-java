package yi.master.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类字段对应数据库查询字段名，包含关联字段或者使用HQL函数的字段
 * @date 20191027
 * @author xuwangcheng
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldNameMapper {
    /**
     * 对应的HQL中的查询名
     * @return
     */
	String fieldPath() default "";

    /**
     * 该字段是否需要被全局模糊查询
     * @return
     */
	boolean ifSearch() default true;

    /**
     * 是否可以被排序
     * @return
     */
	boolean ifOrder() default true;
}
