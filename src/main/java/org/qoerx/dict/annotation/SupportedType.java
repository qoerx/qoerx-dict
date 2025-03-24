package org.qoerx.dict.annotation;

import org.qoerx.dict.matcher.ITypeMatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典转换支持类型注解
 * @Author: wangshuo
 * @Data: 2025/3/21 20:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportedType {
    /**
     * 类型转换策略实现类
     * */
    Class<? extends ITypeMatcher> value();
}
