package com.commerce.ecommerce.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * com.commerce.ECommerce.Controller.*.*(..)) " +
            "|| execution(@org.springframework.web.bind.annotation.GetMapping * com.commerce.ECommerce.Controller.*.*(..)) " +
            "|| execution(@org.springframework.web.bind.annotation.PutMapping * com.commerce.ECommerce.Controller.*.*(..)) " +
            "|| execution(@org.springframework.web.bind.annotation.DeleteMapping * com.commerce.ECommerce.Controller.*.*(..)) " +
            "|| execution(@org.springframework.web.bind.annotation.PostMapping * com.commerce.ECommerce.Controller.*.*(..))")
    public void allControllerMethods() {}

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * com.commerce.ECommerce.Controller.OrderController.*(..)) " +
            "|| execution(@org.springframework.web.bind.annotation.GetMapping * com.commerce.ECommerce.Controller.OrderController.*(..)) " +
            "|| execution(@org.springframework.web.bind.annotation.PostMapping * com.commerce.ECommerce.Controller.OrderController.*(..))")
    public void orderControllerMethods() {}


    @Before("allControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String endpoint = request.getRequestURI();
        String methodName = joinPoint.getSignature().getName();
        log.info("Received {} request for endpoint: {}. Controller method: {}", httpMethod, endpoint, methodName);
    }

    @AfterReturning(pointcut = "allControllerMethods()" +
            "|| orderControllerMethods()", returning = "response")
    public void logAfterReturning(Object response) {
        String httpMethod = request.getMethod();
        String endpoint = request.getRequestURI();
        log.info("Response for {} request to {}: {}", httpMethod, endpoint, response);
    }

    @AfterThrowing("allControllerMethods()")
    public void logAfterThrowing(Throwable e) {
        String httpMethod = request.getMethod();
        String endpoint = request.getRequestURI();
        log.error("Exception in {} request to {}: ", httpMethod, endpoint, e);
    }

    @Around("allControllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Execution time for endpoint {}: {} ms", request.getRequestURI(), executionTime);

        return result;
    }
}
