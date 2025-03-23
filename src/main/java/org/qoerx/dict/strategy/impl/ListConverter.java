package org.qoerx.dict.strategy.impl;

import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.strategy.Converter;

import java.util.List;

/**
 * list 字典转换器
 * @Author: wangshuo
 * @Data: 2025/3/21 17:20
 */
@SupportedType(List.class)
public class ListConverter implements Converter {

    @Override
    public Object convert(Object input) {
        return null;
    }

}
