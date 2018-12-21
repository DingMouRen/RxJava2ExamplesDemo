package com.example.rxjava2.demoapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * Http拦截
 */
@Aspect
@Component
public class HttpAspect {

    private static final Logger sLogger = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 。。表示任何参数都会被拦截
     * *表示ApiController中所有的请求都会被拦截
     */
    @Pointcut("execution(public * com.example.rxjava2.demoapi.controller.ApiController.*(..))")
    public void log(){
    }

    /**
     * 获取请求的内容
     * @Before 注解在请求方法执行之前调用
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        sLogger.info("发起请求：");
        /*url*/
        sLogger.info("请求路径url:{}",request.getRequestURL());
        /*接收的请求方式*/
        sLogger.info("请求方式method:{}",request.getMethod());
        /*ip地址*/
        sLogger.info("ip地址:{}",request.getRemoteAddr());
        /*类的方法*/
        sLogger.info("类的方法:{}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        /*参数*/
        sLogger.info("参数args:{}",joinPoint.getArgs());
    }

    /**
     * 请求结束后调用
     */
    @After("log()")
    public void doAfter(){
        sLogger.info("doAfter");
    }

    /**
     * 获取返回的内容
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAdfterReturning(Object object){
        sLogger.info("收到响应：");
        sLogger.info("reponse={}",object.toString());
    }
}
