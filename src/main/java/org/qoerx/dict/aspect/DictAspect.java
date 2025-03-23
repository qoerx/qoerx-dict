package org.qoerx.dict.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.qoerx.dict.annotation.Dict;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: wangshuo
 * @Data: 2025/3/21 10:43
 */
@Aspect
@Component
@Order(0)
@Slf4j
public class DictAspect {
    //需要扫描的注解包
    private final static String EXPRESSION = "@annotation(org.qoerx.dict.annotation.DictTransform)";
    //读取的字典映射map集合
    private Map<String, Map<String, String>> dictMap = null;

    /**
     * 环绕通知：执行包含对应注解的方式，获取返回值，并进行处理
     *
     * @param joinPoint
     */
    @Around(value = EXPRESSION)
    public Object returningAdvice(ProceedingJoinPoint joinPoint) {
        Object returnVal = null;
        try {
            returnVal = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("org.qoerx.dict.aspect.DictAspect.returningAdvice 执行失败: {} | {} | {}", joinPoint, e, e.getMessage());
        }

        if (returnVal instanceof List) {
            List dataList = (List) returnVal;
            returnVal = setDataList(dataList);
        }

        return returnVal;
    }

    /**
     * 设置集合数据
     * @param dataList 需要映射字典的集合数据
     */
    public List<JSONObject> setDataList(List dataList) {
        return (List<JSONObject>) dataList.stream().map(
                thisColumnProperty -> {
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(thisColumnProperty));
                    setDictProperty(thisColumnProperty, jsonObject, 1);
                    return jsonObject;
                }
        ).collect(Collectors.toList());
    }


    /**
     * 设置字典属性
     * @param columnProperty 需要设置字典数据的对象
     * @param jsonObject 需要设置字典数据的对象
     * @param depth 递归深度，避免出现过深的递归
     */
    public void setDictProperty(Object columnProperty, JSONObject jsonObject, int depth) {
        // 判断递归深度是否过深,过深直接返回,过深会导致堆栈溢出
        if (depth > 3) {
            return;
        }
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
            // 如果没有字典注解，处理引用类型的字段
            if (dictTranslateAnnotation == null) {
                processReferenceTypeField(columnProperty, field, depth);
            } else {
                processDictTranslateField(columnProperty, field, dictTranslateAnnotation, jsonObject);
            }
        }
    }

    /**
     * 处理标有字典注解的字段
     * @param columnProperty 需要设置字典数据的对象
     * @param field 需要进行字典翻译的字段
     * @param dictTranslateAnnotation 字典注解
     */
    private void processDictTranslateField(Object columnProperty, Field field, Dict dictTranslateAnnotation, JSONObject jsonObject) {
        try {
            // 获取需要进行字典翻译的字段的值
            Object dictValue = field.get(columnProperty);
            // 判断需要进行字典翻译的字段的值是否为空
            if (dictValue == null) {
                return;
            }
            String dictCode = dictTranslateAnnotation.code();
            jsonObject.put(field.getName() + "Text", setDictData(dictCode, String.valueOf(dictValue)));
        } catch (Exception e) {
            log.error("DictAspect.processDictTranslateField error: {},{}", e, e.getMessage());
        }
    }

    /**
     * 处理引用类型的字段
     * @param columnProperty 需要设置字典数据的对象
     * @param field 需要进行处理的字段
     * @param depth 递归深度
     */
    private void processReferenceTypeField(Object columnProperty, Field field, int depth) {
        // 获取字段的类型
        Class<?> fieldType = field.getType();
        // 如果是基本类型或字符串类型，直接返回
        if (fieldType.isPrimitive() || fieldType.equals(String.class)) {
            return;
        }
        try {
            // 获取引用类型的值
            Object fieldValue = field.get(columnProperty);
            // 判断引用类型的值是否为空
            if (fieldValue == null) {
                return;
            }
            // 如果是数组类型，处理数组中的元素
            if (fieldType.isArray()) {
                // 获取数组元素的类型
                Class<?> componentType = fieldType.getComponentType();
                // 如果是基本类型或字符串类型，直接返回
                if (componentType.isPrimitive() || componentType.equals(String.class)) {
                    return;
                } else {
                    // 使用Stream API处理数组元素
                    Arrays.stream((Object[]) fieldValue).forEach(element -> {
                        // 递归设置字典属性
//                        setDictProperty(element, depth + 1);
                    });
                }
            } else {
                // 如果是对象类型，递归设置字典属性
//                setDictProperty(fieldValue, depth + 1);
            }
        } catch (Exception e) {
            log.error("DictAspect.processReferenceTypeField error: {},{}", e, e.getMessage());
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
        //获取字典返回
//        Map<String, String> map = dictMap.get(dictCode);
        return "111";
    }

}