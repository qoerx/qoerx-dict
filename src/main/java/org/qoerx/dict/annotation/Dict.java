package org.qoerx.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 * 标注到需要使用的字段上，配合@DictTransform注解使用
 * @Author: wangshuo
 * @Data: 2025/3/18 11:39
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 字典编码
     * */
    String code() default "";
    /**
     * 返回映射字段名称
     * */
    String fieldName() default "";
    /**
     * 是否从枚举中获取
     * */
    boolean isEnum() default false;
    /*
    * 枚举类名
    * */
    Class<?> enumClass() default void.class;
    /**
     * 字典表名
     * */
    String tableName() default "";
}
