package org.qoerx.dict.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * map操作工具类
 * @Author: wangshuo
 * @Data: 2025/3/25 19:46
 */
public class DictUtils {
    /**
     * 将对象转换为Map
     * @param obj 需要转换的对象
     * @return 转换后的Map
     * @throws IllegalAccessException
     */
    public static Map convertToMap(Object obj) throws IllegalAccessException {
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
            Object fieldValue = field.get(obj);
            // 将字段名和字段值放入Map中
            resultMap.put(fieldName, fieldValue);
        }
        return resultMap;
    }
}
