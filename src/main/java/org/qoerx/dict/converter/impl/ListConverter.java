package org.qoerx.dict.converter.impl;

import org.qoerx.dict.annotation.Dict;
import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.converter.IConverter;
import org.qoerx.dict.matcher.impl.ListTypeMatcher;
import org.qoerx.dict.template.ConverterTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * list 字典转换器
 * @Author: wangshuo
 * @Data: 2025/3/21 20:20
 */
@Service
@SupportedType(ListTypeMatcher.class)
public class ListConverter extends ConverterTemplate implements IConverter {

    private static final Logger log = LoggerFactory.getLogger(ListConverter.class);

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
                    Map map = convertToMap(thisColumnProperty);
                    setDictProperty(thisColumnProperty, map);
                    return map;
                }
        ).collect(Collectors.toList());
    }

    /**
     * 将对象转换为Map
     * @param obj 需要转换的对象
     * @return 转换后的Map
     * @throws IllegalAccessException
     */
    public Map convertToMap(Object obj) {
        // 创建一个空的Map
        Map resultMap = new HashMap<>();

        // 获取对象的Class对象
        Class<?> clazz = obj.getClass();

        // 获取所有声明的字段（包括私有字段）
        Field[] fields = clazz.getDeclaredFields();

        // 遍历所有字段
        for (Field field : fields) {
            // 设置字段可访问（即使是私有的）
            field.setAccessible(true);

            // 获取字段名和字段值
            String fieldName = field.getName();
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                log.error("org.qoerx.dict.converter.impl.ListConverter.convertToMap error: {},{}", e, e.getMessage());
            }

            // 将字段名和字段值放入Map中
            resultMap.put(fieldName, fieldValue);
        }

        return resultMap;
    }

    /**
     * 设置字典属性
     * @param columnProperty 需要设置字典数据的对象
     * @param map 需要设置字典数据的对象
     */
    public void setDictProperty(Object columnProperty, Map map) {
        // 判断对象是否为基本类型，如果是则返回
        if (columnProperty.getClass().isPrimitive()) {
            return;
        }
        // 获取所有字段
        Field[] fields = columnProperty.getClass().getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            // 设置字段的可访问性
            field.setAccessible(true);
            // 获取字典注解
            Dict dictTranslateAnnotation = field.getAnnotation(Dict.class);
            if (dictTranslateAnnotation != null) {
                processDictTranslateField(columnProperty, field, dictTranslateAnnotation, map);
            }
        }
    }

    /**
     * 处理标有字典注解的字段
     * @param columnProperty 需要设置字典数据的对象
     * @param field 需要进行字典翻译的字段
     * @param dictTranslateAnnotation 字典注解
     */
    private void processDictTranslateField(Object columnProperty, Field field, Dict dictTranslateAnnotation, Map map) {
        try {
            // 获取需要进行字典翻译的字段的值
            Object dictValue = field.get(columnProperty);
            // 判断需要进行字典翻译的字段的值是否为空
            if (dictValue == null) {
                return;
            }
            String dictCode = dictTranslateAnnotation.code();
            map.put(getFieldName(field), setDictData(dictCode, String.valueOf(dictValue)));
        } catch (Exception e) {
            log.error("org.qoerx.dict.converter.impl.ListConverter.processDictTranslateField error: {},{}", e, e.getMessage());
        }
    }

    /**
     * 设置字典值
     *
     * @param dictCode  字典比编码
     * @param dictValue 字典value
     * @return
     */
    public String setDictData(String dictCode, String dictValue) {
        return String.valueOf(getDictName(dictCode, dictValue));
    }
}
