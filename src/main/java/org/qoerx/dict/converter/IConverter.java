package org.qoerx.dict.converter;

/**
 * 字典转换策略接口
 * @Author: wangshuo
 * @Data: 2025/3/21 20:17
 */
public interface IConverter {
    /**
     * 转换方法
     * */
    Object convert(Object input);
}
