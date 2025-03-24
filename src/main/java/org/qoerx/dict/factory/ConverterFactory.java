package org.qoerx.dict.factory;

import org.qoerx.dict.annotation.SupportedType;
import org.qoerx.dict.converter.IConverter;
import org.qoerx.dict.matcher.ITypeMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换工厂类
 * @Author: wangshuo
 * @Data: 2025/3/24 20:33
 */
@Component
public class ConverterFactory implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ConverterFactory.class);

    private static Map<Class<? extends ITypeMatcher>, Class<? extends IConverter>> converterMap = new HashMap<>();


    /**
     * 获取所有@SupportedType注解标记的bean类
     * 并存入全局converterMap
     * */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(SupportedType.class);
        beansWithAnnotation.forEach((beanName, bean) -> {
            Class<?> beanClass = bean.getClass();
            SupportedType annotation = beanClass.getAnnotation(SupportedType.class);
            Class<? extends ITypeMatcher> supportedType = annotation.value();
            converterMap.put(supportedType, (Class<? extends IConverter>) beanClass);
        });
    }

    /**
     * 获取当前转换策略
     * */
    public Class<? extends IConverter> getConverter(Object returnVal) {
        if (returnVal == null) {
            return null;
        }

        // 遍历 converterMap 的键，寻找第一个匹配的类型
        for (Map.Entry<Class<? extends ITypeMatcher>, Class<? extends IConverter>> entry : converterMap.entrySet()) {
            ITypeMatcher ITypeMatcher = null;
            try {
                ITypeMatcher = entry.getKey().newInstance();
                if (ITypeMatcher.matches(returnVal)) {
                    return entry.getValue();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("org.qoerx.dict.factory.ConverterFactory.getConverter 执行失败: {} | {} | {}", entry.getKey(), e, e.getMessage());
            }
        }

        // 返回null
        return null;
    }
}
