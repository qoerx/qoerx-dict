package org.qoerx.dict.strategy;

/**
 * 字典转换策略接口
 * @Author: wangshuo
 * @Data: 2025/3/21 17:17
 */
public interface Converter {
    /**
     * 转换方法
     * */
    Object convert(Object input);
}
