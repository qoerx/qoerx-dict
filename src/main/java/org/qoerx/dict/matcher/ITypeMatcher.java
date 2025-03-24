package org.qoerx.dict.matcher;

/**
 * 类型匹配接口
 * @Author: wangshuo
 * @Data: 2025/3/21 14:09
 */
public interface ITypeMatcher {

    /**
     * 判断对象是否符合匹配规则
     * */
    boolean matches(Object obj);

}
