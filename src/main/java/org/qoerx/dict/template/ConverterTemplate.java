package org.qoerx.dict.template;

import org.qoerx.dict.config.DictConfig;
import org.qoerx.dict.factory.DictDataFactory;
import org.qoerx.dict.utils.SpringUtils;

import java.lang.reflect.Field;
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

}
