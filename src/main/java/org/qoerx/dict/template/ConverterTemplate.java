package org.qoerx.dict.template;

import org.qoerx.dict.config.DictConfig;
import org.qoerx.dict.constant.DictConstant;
import org.qoerx.dict.factory.DictDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 类型转换模板实体
 * @Author: wangshuo
 * @Data: 2025/3/24 20:34
 */
public class ConverterTemplate {

    private static final Logger log = LoggerFactory.getLogger(ConverterTemplate.class);

    /**
     * 获取返回字段名称
     * */
    public String getFieldName(Field field) {
        // 获取返回字段名称
        String suffix = DictConstant.DICT_SUFFIX;
        try {
            suffix = DictConfig.class.newInstance().getSuffix();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("获取返回字段名称失败", e);
        }
        return field.getName() + suffix;
    }

    /**
     * 获取字典数据工厂
     * */
    public DictDataFactory getDictDataFactory() {
        DictDataFactory dictDataFactory = null;
        try {
            dictDataFactory = DictDataFactory.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("获取字典数据工厂失败", e);
        }
        return dictDataFactory;
    }


    /**
     * 根据字典code获取字典Map数据
     * */
    public Map<String, String> getDictDataMap(Object dictCode) {
        return getDictDataFactory().getDictDataMap(dictCode);
    }

    /**
     * 根据字典code和字典value获取字典name
     * */
    public Object getDictName(Object dictCode, Object dictValue) {
        return getDictDataFactory().getDictName(dictCode, dictValue);
    }

    /**
     * 根据字典code和字典name获取字典value
     * */
    public Object getDictValue(Object dictCode, Object dictName) {
        return getDictDataFactory().getDictValue(dictCode, dictName);
    }


}
