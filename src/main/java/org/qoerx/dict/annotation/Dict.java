package org.qoerx.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 * 标注到需要使用的字段上，配合@DictTransform注解使用
 * @Author: wangshuo
 * @Data: 2025/3/18 21:39
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dict {
    /**
     * 字典编码
     * */
    String code() default "";
    /**
     * 是否正向映射
     * true正向映射为从缓存的字典编码的key值查找 value值为替换的值
     * false反向映射为从缓存的字典编码的value值查找 key值为替换的值
     * */
    boolean forward() default true;
    /**
     * 字典转换类型
     * 1、code转换获取 必选参数code 可选参数forward
     * 2、枚举中获取 必选参数enumClass、enumValue、enumTitle
     * 可自行扩展，提供自定义参数params
     * 对应转换方法需要实现 ITypeTransform 标识@TypeTransform注解
     * */
    int type() default 1;
    /**
     * 枚举类路径
     * */
    Class<?> enumClass() default Void.class;
    /**
     * 枚举value值
     * */
    String enumValue() default "";
    /**
     * 枚举title值
     * */
    String enumTitle() default "";
    /**
     * 自定义参数
     * */
    String[] params() default {};
}
