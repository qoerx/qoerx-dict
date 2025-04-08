package org.qoerx.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典转换标记注解
 * 标注到控制层方法中，会将返回字段中包含@Dict注解的字段进行字典转换
 * @Author: wangshuo
 * @Data: 2025/3/21 20:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DictTransform {
    /**
     * 非默认map的key值情况下使用，默认为空
     * 当value存在时，使用value的值在map中进行检索
     * 例：
     * 当map的key为result时，由于默认key值为data，
     * 这时不进行转换，若想进行转换，需加注解@DictTransform("result")
     * 自定义注解情况下可以使value值为需要的值
     * */
    String value() default "";
}
