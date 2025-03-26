package org.qoerx.dict.template;

import org.qoerx.dict.annotation.Dict;
import org.qoerx.dict.config.DictConfig;
import org.qoerx.dict.factory.DictDataFactory;
import org.qoerx.dict.factory.TypeTransformFactory;
import org.qoerx.dict.strategy.ITypeTransform;
import org.qoerx.dict.utils.DictUtils;
import org.qoerx.dict.utils.SpringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换模板实体
 * @Author: wangshuo
 * @Data: 2025/3/24 20:34
 */
public class ConverterTemplate {

    /**
     * 获取返回字段名称
     * */
    public String getFieldName(Field field) {
        return field.getName() + SpringUtils.getBean(DictConfig.class).getSuffix();
    }

    /**
     * 根据字典code获取字典Map数据
     * */
    public Map<String, String> getDictDataMap(Object dictCode) {
        return SpringUtils.getBean(DictDataFactory.class).getDictDataMap(dictCode);
    }

    /**
     * 根据字典code和字典value获取字典name
     * */
    public Object getDictName(Object dictCode, Object dictValue) {
        return SpringUtils.getBean(DictDataFactory.class).getDictName(dictCode, dictValue);
    }

    /**
     * 根据字典code和字典name获取字典value
     * */
    public Object getDictValue(Object dictCode, Object dictName) {
        return SpringUtils.getBean(DictDataFactory.class).getDictValue(dictCode, dictName);
    }

    /**
     * 设置字典属性
     * @param columnProperty 需要设置字典数据的对象
     * @param map 需要设置字典数据的对象
     */
    public void setDictProperty(Object columnProperty, Map map) throws IllegalAccessException {
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
     * @param dict 字典注解
     * @param map 需要设置字典数据的对象
     */
    private void processDictTranslateField(Object columnProperty, Field field, Dict dict, Map map) throws IllegalAccessException {
        // 获取需要进行字典翻译的字段的值
        Object dictValue = field.get(columnProperty);
        // 判断需要进行字典翻译的字段的值是否为空
        if (dictValue == null) {
            return;
        }
        ITypeTransform typeTransform = SpringUtils.getBean(TypeTransformFactory.class).getTypeTransform(dict.type());
        map.put(getFieldName(field), typeTransform.transform(dict, String.valueOf(dictValue)));
    }
}
