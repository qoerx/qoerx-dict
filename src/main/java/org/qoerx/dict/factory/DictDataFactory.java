package org.qoerx.dict.factory;

import org.qoerx.dict.annotation.DictData;
import org.qoerx.dict.data.IDictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 字典数据工厂类
 * @Author: wangshuo
 * @Data: 2025/3/24 20:29
 */
@Component
public class DictDataFactory implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(DictDataFactory.class);

    private static Map<Object, Map> dictDataMap = new HashMap();

    /**
     * 获取所有@DictData注解标记的bean类
     * 并将结果集存入全局dictDataMap
     * */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(DictData.class);
        beansWithAnnotation.forEach((beanName, bean) -> {
            Class<IDictData> beanClass = (Class<IDictData>) bean.getClass();
            try {
                dictDataMap.putAll(beanClass.newInstance().getDictDataMap());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("获取字典数据失败", e);
            }
        });
    }

    /**
     * 根据字典code获取字典Map数据
     * */
    public Map<String, String> getDictDataMap(Object dictCode) {
        return dictDataMap.get(dictCode);
    }

    /**
     * 根据字典code和字典value获取字典name
     * */
    public Object getDictName(Object dictCode, Object dictValue) {
        Map dictDataMap = getDictDataMap(dictCode);
        return dictDataMap.get(dictValue);
    }

    /**
     * 根据字典code和字典name获取字典value
     * */
    public Object getDictValue(Object dictCode, Object dictName) {
        AtomicReference<Object> dictValue = new AtomicReference<>();
        Map dictDataMap = getDictDataMap(dictCode);
        dictDataMap.forEach((k, v) -> {
            if (v.equals(dictName)) {
                dictValue.set(k);
            }
        });
        return dictValue;
    }

}
