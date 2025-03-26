package org.qoerx.dict.strategy;

/**
 * 字典转换策略接口
 * @Author: wangshuo
 * @Data: 2025/3/21 20:17
 */
public interface IConverter {
    /**
     * 判断对象是否符合匹配规则
     * */
    boolean matches(Object input) throws Exception;
    /**
     * 转换方法
     * */
    Object convert(Object input);
}
