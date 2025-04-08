package org.qoerx.dict.strategy;

/**
 * 字典转换策略接口
 * @Author: wangshuo
 * @Data: 2025/3/21 20:17
 */
public interface IConverter {
    /**
     * 判断对象是否符合匹配规则
     * @param input 输入对象
     * @param transformValue @DictTransform 注解值
     * @return 是否匹配
     * */
    boolean matches(Object input, String transformValue) throws Exception;
    /**
     * 转换方法
     * @param input 输入对象
     * @param transformValue @DictTransform 注解值
     * @return 转换后的对象
     * */
    Object convert(Object input, String transformValue);
}
