package org.qoerx.dict.strategy;

import org.qoerx.dict.annotation.Dict;

/**
 * 字典转换类型接口
 * @Author: wangshuo
 * @Data: 2025/3/26 19:44
 */
public interface ITypeTransform {
    /**
     * 获取字典转换后的数据
     * */
    Object transform(Dict dict, Object dictValue);
}
