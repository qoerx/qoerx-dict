package org.qoerx.dict.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.qoerx.dict.factory.ConverterFactory;
import org.qoerx.dict.converter.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

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

    //需要扫描的注解包
    private final static String EXPRESSION = "@annotation(org.qoerx.dict.annotation.DictTransform)";
    //读取的字典映射map集合
    private Map<String, Map<String, String>> dictMap = null;

    /**
     * 环绕通知：执行包含对应注解的方式，获取返回值，并进行处理
     *
     * @param joinPoint
     */
    @Around(value = EXPRESSION)
    public Object returningAdvice(ProceedingJoinPoint joinPoint) {
        Object returnVal = null;
        try {
            returnVal = joinPoint.proceed();
            Class<? extends IConverter> converter = converterFactory.getConverter(returnVal);
            returnVal = converter.newInstance().convert(returnVal);
        } catch (Throwable e) {
            log.error("org.qoerx.dict.aspect.DictAspect.returningAdvice 执行失败: {} | {} | {}", joinPoint, e, e.getMessage());
            return returnVal;
        }
        return returnVal;
    }

}