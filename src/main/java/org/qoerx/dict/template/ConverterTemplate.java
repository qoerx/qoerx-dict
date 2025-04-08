package org.qoerx.dict.template;

import com.alibaba.fastjson2.JSONObject;
import org.qoerx.dict.annotation.Dict;
import org.qoerx.dict.config.DictConfig;
import org.qoerx.dict.factory.DictDataFactory;
import org.qoerx.dict.factory.TypeTransformFactory;
import org.qoerx.dict.strategy.ITypeTransform;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * 类型转换模板实体
 * @Author: wangshuo
 * @Data: 2025/3/24 20:34
 */
public class ConverterTemplate {
    @Resource
    public ApplicationContext applicationContext;

    /**
     * 获取map实体所用key值
     * @param map map实体
     * @param transformValue 字典转换类型
     * */
    public String getRKey(Map map, String transformValue) {
        if (transformValue != null && !transformValue.isEmpty()){
            return transformValue;
        }
        return applicationContext.getBean(DictConfig.class).getMapKey();
    }

    /**
     * 获取返回字段名称
     * */
    public String getFieldName(Field field) {
        return field.getName() + applicationContext.getBean(DictConfig.class).getSuffix();
    }

    /**
     * 根据字典code获取字典Map数据
     * */
    public Map<String, String> getDictDataMap(Object dictCode) {
        return applicationContext.getBean(DictDataFactory.class).getDictDataMap(dictCode);
    }

    /**
     * 根据字典code和字典value获取字典name
     * */
    public Object getDictName(Object dictCode, Object dictValue) {
        return applicationContext.getBean(DictDataFactory.class).getDictName(dictCode, dictValue);
    }

    /**
     * 根据字典code和字典name获取字典value
     * */
    public Object getDictValue(Object dictCode, Object dictName) {
        return applicationContext.getBean(DictDataFactory.class).getDictValue(dictCode, dictName);
    }

    /**
     * 设置字典属性
     * @param columnProperty 需要设置字典数据的对象
     * @param map 需要设置字典数据的对象
     * @param depth 递归深度
     */
    public void setDictProperty(Object columnProperty, JSONObject map, Integer depth) throws IllegalAccessException {
        //判断是否超过最大递归深度
        if (depth > applicationContext.getBean(DictConfig.class).getDepth()) {
            return;
        }
        Class<?> aClass = columnProperty.getClass();

        // 判断对象是否为基本类型，如果是则返回
        if (aClass.isPrimitive()) {
            return;
        }
        // 获取所有字段
        Field[] fields = aClass.getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            // 设置字段的可访问性
            field.setAccessible(true);
            // 获取字典注解
            Dict dictTranslateAnnotation = field.getAnnotation(Dict.class);
            if (dictTranslateAnnotation != null) {
                processDictTranslateField(columnProperty, field, dictTranslateAnnotation, map);
            }
            // 判断是否为子实体并递归处理
            try {
                if (isSubEntity(field)) {
                    Object subEntity = field.get(columnProperty);
                    if (subEntity != null) {
                        Object object = map.get(field.getName());
                        JSONObject jsonObject = JSONObject.parseObject(object.toString());
                        setDictProperty(subEntity, jsonObject, depth + 1);
                        map.put(field.getName(), jsonObject);
                    }
                }
            } catch (Exception e) {
                System.out.println("判断是否为子实体失败,递归深度：" + (depth + 1) + "失败" );
            }
        }
    }

    // 判断字段是否为子实体
    private boolean isSubEntity(Field field) {
        Class<?> fieldType = field.getType();
        return !fieldType.isPrimitive()
                && !fieldType.equals(String.class)
                && !fieldType.equals(Integer.class)
                && !fieldType.equals(Long.class)
                && !fieldType.equals(Double.class)
                && !fieldType.equals(Float.class)
                && !fieldType.equals(Boolean.class)
                && !fieldType.equals(BigDecimal.class)
                && !Collection.class.isAssignableFrom(fieldType)
                && !fieldType.isArray()
                && !Map.class.isAssignableFrom(fieldType);
    }

    /**
     * 处理标有字典注解的字段
     * @param columnProperty 需要设置字典数据的对象
     * @param field 需要进行字典翻译的字段
     * @param dict 字典注解
     * @param map 需要设置字典数据的对象
     */
    private void processDictTranslateField(Object columnProperty, Field field, Dict dict, JSONObject map) throws IllegalAccessException {
        // 获取需要进行字典翻译的字段的值
        Object dictValue = field.get(columnProperty);
        // 判断需要进行字典翻译的字段的值是否为空
        if (dictValue == null) {
            return;
        }
        ITypeTransform typeTransform = applicationContext.getBean(TypeTransformFactory.class).getTypeTransform(dict.type());
        map.put(getFieldName(field), typeTransform.transform(dict, String.valueOf(dictValue)));
    }
}
