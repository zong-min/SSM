package com.it.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect   // 该类是一个切面类
@Component
public class MyAspect {

    // 获取日志对象
    private static final Logger logger= LoggerFactory.getLogger(MyAspect.class);

    // 定义切入点表达式
    @Pointcut("execution(* com.it.service.*.*(..))")
    private void myPointCut() {
    }

    // 前置通知(切入点方法执行之前执行)，注解内的值既可以是表达式，也可以是表达式的引用
    @Before("myPointCut()")
    public void myBefore(JoinPoint joinPoint) {
        logger.info("进入了" + joinPoint.getSignature().getName() + "方法");
    }

    @AfterReturning("myPointCut()")
    public void myAfter(JoinPoint joinPoint) {
        logger.info("退出了" + joinPoint.getSignature().getName() + "方法");
    }
}
