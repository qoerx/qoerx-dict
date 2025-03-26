package org.qoerx.dict.strategy.impl;

import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.strategy.IConverter;
import org.qoerx.dict.template.ConverterTemplate;
import org.qoerx.dict.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * List<T> 类型字典转换器
 * T包含@Dict注解的实体类
 * @Author: wangshuo
 * @Data: 2025/3/21 20:20
 */
@Service
@SupportedType
public class ListTConverter extends ConverterTemplate implements IConverter {

    private static final Logger log = LoggerFactory.getLogger(ListTConverter.class);

    @Override
    public boolean matches(Object input){
        return List.class.isAssignableFrom(input.getClass());
    }

    @Override
    public Object convert(Object returnVal) {
        List dataList = (List) returnVal;
        returnVal = setDataList(dataList);
        return returnVal;
    }

    /**
     * 设置集合数据
     * @param dataList 需要映射字典的集合数据
     */
    public List<Map> setDataList(List dataList) {
        return (List<Map>) dataList.stream().map(
                thisColumnProperty -> {
                    //实体类转map
                    Map map = null;
                    try {
                        map = DictUtils.convertToMap(thisColumnProperty);
                        setDictProperty(thisColumnProperty, map);
                    } catch (IllegalAccessException e) {
                        log.error("org.qoerx.dict.converter.impl.ListTConverter.setDataList 执行失败: \n{}\n{}", e, e.getMessage());
                    }
                    return map;
                }
        ).collect(Collectors.toList());
    }

}
