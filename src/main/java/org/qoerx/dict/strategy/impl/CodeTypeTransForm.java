package org.qoerx.dict.strategy.impl;

import org.qoerx.dict.annotation.Dict;
import org.qoerx.dict.annotation.TypeTransform;
import org.qoerx.dict.factory.DictDataFactory;
import org.qoerx.dict.strategy.ITypeTransform;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * code转换获取对应字典数据的实现
 * @Author: wangshuo
 * @Data: 2025/3/26 14:51
 */
@Service
@TypeTransform(1)
public class CodeTypeTransForm implements ITypeTransform {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public Object transform(Dict dict, Object dictValue) {
        String dictCode = dict.code();
        boolean forward = dict.forward();
        return getDictData(dictCode, String.valueOf(dictValue), forward);
    }

    /**
     * 设置字典值
     *
     * @param dictCode  字典编码
     * @param dictValue 字典value
     * @param forward   是否为正向映射
     * @return
     */
    public Object getDictData(String dictCode, String dictValue, boolean forward) {
        DictDataFactory dictDataFactory = applicationContext.getBean(DictDataFactory.class);
        if (forward) {
            return dictDataFactory.getDictName(dictCode, dictValue);
        }
        return dictDataFactory.getDictValue(dictCode, dictValue);
    }
}
