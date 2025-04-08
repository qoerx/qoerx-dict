package org.qoerx.dict.strategy.impl;

import com.alibaba.fastjson2.JSONObject;
import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.strategy.IConverter;
import org.qoerx.dict.template.ConverterTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public boolean matches(Object input, String transformValue){
        return List.class.isAssignableFrom(input.getClass());
    }

    @Override
    public Object convert(Object returnVal, String transformValue) {
        List dataList = (List) returnVal;
        returnVal = setDataList(dataList);
        return returnVal;
    }

    /**
     * 设置集合数据
     * @param dataList 需要映射字典的集合数据
     */
    public List<JSONObject> setDataList(List dataList) {
        return (List<JSONObject>) dataList.stream().map(
                thisColumnProperty -> {
                    //实体类转map
                    JSONObject map = null;
                    try {
                        map = JSONObject.parseObject(JSONObject.toJSONString(thisColumnProperty));;
                        setDictProperty(thisColumnProperty, map, 1);
                    } catch (IllegalAccessException e) {
                        log.error("org.qoerx.dict.converter.impl.ListTConverter.setDataList 执行失败: \n{}\n{}", e, e.getMessage());
                    }
                    return map;
                }
        ).collect(Collectors.toList());
    }

}
