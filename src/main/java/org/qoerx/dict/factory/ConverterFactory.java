package org.qoerx.dict.factory;

import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.strategy.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类型转换工厂类
 * @Author: wangshuo
 * @Data: 2025/3/24 20:33
 */
@Component
public class ConverterFactory {

    private static final Logger log = LoggerFactory.getLogger(ConverterFactory.class);

    /**
     * 转换策略集合
     * */
    private static List<Class<? extends IConverter>> converterList = new ArrayList<>();

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 获取所有@SupportedType注解标记的bean类
     * 并存入全局converterList
     * */
    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SupportedType.class);
        beansWithAnnotation.forEach((beanName, bean) -> {
            Class<?> beanClass = bean.getClass();
            converterList.add((Class<? extends IConverter>) beanClass);
        });
    }

    /**
     * 获取当前转换策略
     * */
    public Class<? extends IConverter> getConverter(Object returnVal, String transformValue) {
        if (returnVal == null) {
            return null;
        }

        // 遍历 converterList，寻找第一个匹配的类型
        for (Class<? extends IConverter> converterClass : converterList) {
            boolean found = false;
            try {
                found = applicationContext.getBean(converterClass).matches(returnVal, transformValue);
            } catch (Exception e) {
                log.error("org.qoerx.dict.factory.ConverterFactory.getConverter 执行失败: \n{}\n{}\n{}", returnVal, e, e.getMessage());
            }
            if (found){
                return converterClass;
            }
        }

        // 返回null
        return null;
    }
}
