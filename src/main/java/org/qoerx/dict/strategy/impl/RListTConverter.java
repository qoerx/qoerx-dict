package org.qoerx.dict.strategy.impl;

import com.alibaba.fastjson2.JSONObject;
import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.strategy.IConverter;
import org.qoerx.dict.template.ConverterTemplate;
import org.qoerx.dict.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * R<List<T>> 类型字典转换器
 * R结果集实体类，一般为指定返回参数实体
 * T包含@Dict注解的实体类
 * @Author: wangshuo
 * @Data: 2025/3/25 19:31
 */
@Service
@SupportedType
public class RListTConverter extends ConverterTemplate implements IConverter {

    private static final Logger log = LoggerFactory.getLogger(RListTConverter.class);

    @Override
    public boolean matches(Object input, String transformValue) {
        Map map = null;
        try {
            map = DictUtils.convertToMap(input);
        } catch (IllegalAccessException e) {
            log.error("org.qoerx.dict.converter.impl.RListTConverter.matches 执行失败: \n{}\n{}", e, e.getMessage());
        }
        if (map != null && !map.isEmpty()){
            String mapKey = getRKey(map, transformValue);
            Object list = map.get(mapKey);
            if (list != null){
                return list instanceof List;
            }
        }
        return false;
    }

    @Override
    public Object convert(Object input, String transformValue) {
        Class<?> aClass = input.getClass();
        Map map = null;
        try {
            map = DictUtils.convertToMap(input);
        } catch (IllegalAccessException e) {
            log.error("org.qoerx.dict.converter.impl.RListTConverter.convert 执行失败: \n{}\n{}", e, e.getMessage());
        }
        if (map != null && !map.isEmpty()) {
            String mapKey = getRKey(map, transformValue);
            Object list = map.get(mapKey);
            if (list != null) {
                if (list instanceof List) {
                    ListTConverter listTConverter = applicationContext.getBean(ListTConverter.class);
                    List<JSONObject> dataList = listTConverter.setDataList((List) list);
                    map.put(mapKey, dataList);
                }
            }
        }
        String json = JSONObject.toJSONString(map);
        return JSONObject.parseObject(json, aClass);
    }
}
