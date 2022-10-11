package com.project.courseservice.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class Aspects {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.project.courseservice.advice.TrackExecutionTime)")
    public void logExecTimePointCut(){}

    @Around("logExecTimePointCut()")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        logger.warn("[Time]Class: {}. Method: {}. Executed in: {} ms", point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), (endTime-startTime));
        return object;
    }


    @Around("@annotation(com.project.courseservice.advice.TrackParameters)")
    public Object params(ProceedingJoinPoint point) throws Throwable {
        logger.warn("[Parameters]Class: {}. Method: {}. Arguments: {}", point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), Arrays.toString(point.getArgs()) );
        Object proceed = point.proceed();
        logger.warn("[Parameters]Class: {} Method: {}. Return: {}",
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), proceed);
        return proceed;
    }
}
