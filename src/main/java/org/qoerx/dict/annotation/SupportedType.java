package org.qoerx.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典转换支持类型注解
 * @Author: wangshuo
 * @Data: 2025/3/21 17:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportedType {
    /**
     * 转换类型
     * */
    Class<?> value();
}
