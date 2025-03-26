package org.qoerx.dict.factory;

import org.qoerx.dict.annotation.DictData;
import org.qoerx.dict.strategy.IDictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 字典数据工厂类
 * @Author: wangshuo
 * @Data: 2025/3/24 20:29
 */
@Component
public class DictDataFactory {

    private static final Logger log = LoggerFactory.getLogger(DictDataFactory.class);

    @Resource
    private ApplicationContext applicationContext;

    private static Map<Object, Map> dictDataMap = new HashMap();

    /**
     * 获取所有@DictData注解标记的bean类
     * 并将结果集存入全局dictDataMap
     * */
    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(DictData.class);
        beansWithAnnotation.forEach((beanName, bean) -> {
            Class<IDictData> beanClass = (Class<IDictData>) bean.getClass();
            dictDataMap.putAll(applicationContext.getBean(beanClass).getDictDataMap());
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
     * @param dictCode 字典code
     * @param dictValue 字典value
     * @return ""
     * */
    public Object getDictName(Object dictCode, Object dictValue) {
        try {
            Map dictDataMap = getDictDataMap(dictCode);
            return dictDataMap.get(dictValue);
        }catch (Exception e) {
            log.error("org.qoerx.dict.factory.DictDataFactory.getDictName 执行失败: \n{}\n{}", e, e.getMessage());
        }
        return "";
    }

    /**
     * 根据字典code和字典name获取字典value
     * @param dictCode 字典code
     * @param dictName 字典name
     * @return ""
     * */
    public Object getDictValue(Object dictCode, Object dictName) {
        try {
            AtomicReference<Object> dictValue = new AtomicReference<>();
            Map dictDataMap = getDictDataMap(dictCode);
            dictDataMap.forEach((k, v) -> {
                if (v.equals(dictName)) {
                    dictValue.set(k);
                }
            });
            return dictValue;
        }catch (Exception e) {
            log.error("org.qoerx.dict.factory.DictDataFactory.getDictValue 执行失败: \n{}\n{}", e, e.getMessage());
        }
        return "";
    }

}
