package org.qoerx.dict.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Spring 工具类
 * @Author: wangshuo
 * @Data: 2025/3/25 19:13
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 获取 ApplicationContext
     * */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过名称获取 Bean
     * */
    public static <T> T getBean(String name) {
        Assert.notNull(applicationContext, "ApplicationContext未初始化！");
        return (T) applicationContext.getBean(name);
    }

    /**
     * 通过类型获取 Bean
     * */
    public static <T> T getBean(Class<T> requiredType) {
        Assert.notNull(applicationContext, "ApplicationContext未初始化！");
        return applicationContext.getBean(requiredType);
    }

    /**
     * 通过名称和类型获取 Bean
     * */
    public static <T> T getBean(String name, Class<T> requiredType) {
        Assert.notNull(applicationContext, "ApplicationContext未初始化！");
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 获取所有指定类型的 Bean
     * */
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        Assert.notNull(applicationContext, "ApplicationContext未初始化！");
        return applicationContext.getBeansOfType(requiredType);
    }

    /**
     * 动态注册 Bean（需要 ConfigurableApplicationContext）
     * */
    public static void registerBean(String name, Object bean) {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) applicationContext).getBeanFactory().registerSingleton(name, bean);
        }
    }

    /**
     * 发布事件
     * */
    public static void publishEvent(ApplicationEvent event) {
        Assert.notNull(applicationContext, "ApplicationContext未初始化！");
        applicationContext.publishEvent(event);
    }
}
