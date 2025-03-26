package org.qoerx.dict.factory;

import org.qoerx.dict.annotation.TypeTransform;
import org.qoerx.dict.strategy.ITypeTransform;
import org.qoerx.dict.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangshuo
 * @Data: 2025/3/26 19:58
 */
@Component
public class TypeTransformFactory {
    private static final Logger log = LoggerFactory.getLogger(TypeTransformFactory.class);

    @Resource
    private ApplicationContext applicationContext;

    private static Map<Integer, Class<? extends ITypeTransform>> typeMap = new HashMap();

    /**
     * 获取所有@TypeTransform注解标记的bean类
     * 并存入全局typeMap
     * */
    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(TypeTransform.class);
        beansWithAnnotation.forEach((beanName, bean) -> {
            Class<?> beanClass = bean.getClass();
            TypeTransform annotation = beanClass.getAnnotation(TypeTransform.class);
            int value = annotation.value();
            typeMap.put(value, (Class<? extends ITypeTransform>) beanClass);
        });
    }

    public ITypeTransform getTypeTransform(int type) {
        Class<? extends ITypeTransform> aClass = typeMap.get(type);
        if (aClass == null) {
            log.error("没有找到对应的转换器");
            return null;
        }
        return SpringUtils.getBean(aClass);
    }
}
