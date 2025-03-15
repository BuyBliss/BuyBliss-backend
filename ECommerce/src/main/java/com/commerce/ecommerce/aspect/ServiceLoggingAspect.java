package com.commerce.ecommerce.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    //@Pointcut("execution(public * com.commerce.ecommerce.service.*.*(..))")       //(method level pointcut)
    @Pointcut("within(com.commerce.ecommerce.service..*)")      //(simple class level pointcut)
    public void allServiceMethods() {}

    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Method Entry : {}", joinPoint.getSignature().getName());
    }

    @After("allServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Method Exit : {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(public * com.commerce.ecommerce.service.OrderService.buyNow(..))", returning = "result")
    public void logAfterReturning(Object result) {
        try {
            log.info("Method returned : {}", result);
        } catch (Exception e) {
            log.error("Exception occurred while returning an object : ", e);
        }
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "e")
    public void logAfterThrowing(Throwable e) {
        log.error("Exception : ", e);
    }
}
