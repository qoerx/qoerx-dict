package org.qoerx.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典转换注解
 * 标注到控制层方法中，会将返回字段中包含@Dict注解的字段进行字典转换
 * @Author: wangshuo
 * @Data: 2025/3/21 10:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DictTransform {
}
