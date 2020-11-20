package com.dj.iotlite.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Slf4j
@Configuration
public class ApiAop {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Around("execution(* com.dj.iotlite.api.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ret=joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        doApiAudioLog();
        doCheckAPICallLimit();
        doCheckApiSign();
        doCheckAccess();

        return ret;
    }
    //TODO api访问日志
    void  doApiAudioLog(){

    };
    //TODO api限流
    void doCheckAPICallLimit(){

    }
    //TODO 签名验证
    void doCheckApiSign(){

    }
    //TODO API访问验证
    void doCheckAccess(){

    }

}
