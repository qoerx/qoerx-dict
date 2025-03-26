package org.qoerx.dict.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.config.DictConfig;
import org.qoerx.dict.strategy.IConverter;
import org.qoerx.dict.template.ConverterTemplate;
import org.qoerx.dict.utils.DictUtils;
import org.qoerx.dict.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * R<T> 类型字典转换器
 * R 结果集实体类，一般为指定返回参数实体
 * T 包含@Dict注解的实体类
 * @Author: wangshuo
 * @Data: 2025/3/25 20:37
 */
@Service
@SupportedType
public class RTConverter extends ConverterTemplate implements IConverter {
    private static final Logger log = LoggerFactory.getLogger(RTConverter.class);

    @Override
    public boolean matches(Object input) {
        Map map = null;
        try {
            map = DictUtils.convertToMap(input);
        if (map != null && !map.isEmpty()){
            Object obj = map.get(SpringUtils.getBean(DictConfig.class).getMapKey());
            Map objMap = DictUtils.convertToMap(obj);
            if (objMap != null && !objMap.isEmpty()){
                return true;
            }
        }
        } catch (IllegalAccessException e) {
            log.error("org.qoerx.dict.converter.impl.RTConverter.matches 执行失败: \n{}\n{}", e, e.getMessage());
        }
        return false;
    }

    @Override
    public Object convert(Object input) {
        Class<?> aClass = input.getClass();
        Map map = null;
        try {
            map = DictUtils.convertToMap(input);

            if (map != null && !map.isEmpty()){
                Object obj = map.get(SpringUtils.getBean(DictConfig.class).getMapKey());
                Map objMap = DictUtils.convertToMap(obj);
                if (objMap != null && !objMap.isEmpty()){
                    setDictProperty(obj, objMap);
                }
                map.put(SpringUtils.getBean(DictConfig.class).getMapKey(), objMap);
            }
        } catch (IllegalAccessException e) {
            log.error("org.qoerx.dict.converter.impl.RTConverter.convert 执行失败: \n{}\n{}", e, e.getMessage());
        }
        String json = JSONObject.toJSONString(map);
        return JSONObject.parseObject(json, aClass);
    }
}
