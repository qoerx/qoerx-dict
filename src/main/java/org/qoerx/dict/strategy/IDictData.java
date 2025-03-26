package org.qoerx.dict.strategy;

import java.util.Map;

/**
 * 字典数据接口
 * @Author: wangshuo
 * @Data: 2025/3/24 20:21
 */
public interface IDictData {

    /**
     * 获取字典数据集合
     * */
    Map<Object, Map> getDictDataMap();
}
