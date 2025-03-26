package org.qoerx.dict.strategy.impl;

import org.qoerx.dict.annotation.Dict;
import org.qoerx.dict.annotation.TypeTransform;
import org.qoerx.dict.strategy.ITypeTransform;
import org.qoerx.dict.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 从枚举中获取对应字典数据的实现
 * @Author: wangshuo
 * @Data: 2025/3/26 14:56
 */
@Service
@TypeTransform(2)
public class EnumTypeTransForm implements ITypeTransform {
    private static final Logger log = LoggerFactory.getLogger(EnumTypeTransForm.class);

    @Override
    public Object transform(Dict dict, Object dictValue) {
        // 获取注解中的枚举类
        Class<?> enumClass = dict.enumClass();
        if (enumClass == Void.class || !enumClass.isEnum()) {
            log.error("org.qoerx.dict.strategy.impl.EnumTypeTransForm.transform 无效的枚举类: \n{}", enumClass.getName());
            return "";
        }

        // 获取注解中的 value 和 title 的get方法
        String enumValueField = DictUtils.toGetterMethodName(dict.enumValue());
        String enumTitleField = DictUtils.toGetterMethodName(dict.enumTitle());

        if (enumValueField.isEmpty() || enumTitleField.isEmpty()) {
            log.error("org.qoerx.dict.strategy.impl.EnumTypeTransForm.transform 枚举value值和枚举title值必须必填");
            return "";
        }

        try {
            // 将枚举转为 HashMap
            Map<String, String> enumMap = new HashMap<>();
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                Class<?> aClass = enumConstant.getClass();
                Method methodValue = aClass.getDeclaredMethod(enumValueField);
                methodValue.setAccessible(true);
                String value = String.valueOf(methodValue.invoke(enumConstant));
                Method methodTitle = aClass.getDeclaredMethod(enumTitleField);
                methodTitle.setAccessible(true);
                String title = String.valueOf(methodTitle.invoke(enumConstant));
                enumMap.put(value, title);
            }

            return enumMap.get(dictValue);
        } catch (Exception e) {
            log.error("org.qoerx.dict.strategy.impl.EnumTypeTransForm.transform 转换枚举失败: \n{}\n{}\n{}", enumClass.getName(), e, e.getMessage());
            return "";
        }
    }
}
