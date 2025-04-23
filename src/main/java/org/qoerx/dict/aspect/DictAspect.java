package org.qoerx.dict.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.qoerx.dict.annotation.DictTransform;
import org.qoerx.dict.factory.ConverterFactory;
import org.qoerx.dict.strategy.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @Author: wangshuo
 * @Data: 2025/3/21 20:43
 */
@Aspect
@Component
@Order(0)
public class DictAspect {

    private static final Logger log = LoggerFactory.getLogger(DictAspect.class);

    @Resource
    private ConverterFactory converterFactory;
    @Resource
    private ApplicationContext applicationContext;

    //需要扫描的注解包
    private final static String EXPRESSION = "@annotation(org.qoerx.dict.annotation.DictTransform)";

    /**
     * 环绕通知：执行包含对应注解的方式，获取返回值，并进行处理
     *
     * @param joinPoint
     */
    @Around(value = EXPRESSION)
    public Object returningAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取注解@DictTransform对应的值
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DictTransform annotation = method.getAnnotation(DictTransform.class);
        String value = annotation.value();
        Object returnVal = joinPoint.proceed();
        try {
            Class<? extends IConverter> converter = converterFactory.getConverter(returnVal, value);
            returnVal = applicationContext.getBean(converter).convert(returnVal, value);
        } catch (Throwable e) {
            log.error("org.qoerx.dict.aspect.DictAspect.returningAdvice 执行失败: \n{}\n{}\n{}", joinPoint, e, e.getMessage());
            return returnVal;
        }
        return returnVal;
    }

}