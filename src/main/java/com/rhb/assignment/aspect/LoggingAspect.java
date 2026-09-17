package com.rhb.assignment.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private final static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.rhb.assignment.controller..*(..))")
    public Object logRequestAndResponse(
            ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();

        logger.info(
                "REQUEST -> Method: {}, Arguments: {}",
                methodName,
                Arrays.toString(joinPoint.getArgs())
        );

        Object response = joinPoint.proceed();

        logger.info(
                "RESPONSE -> Method: {}, Response: {}",
                methodName,
                response
        );

        return response;
    }
}
