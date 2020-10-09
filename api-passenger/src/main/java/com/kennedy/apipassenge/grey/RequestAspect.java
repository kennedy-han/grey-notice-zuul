package com.kennedy.apipassenge.grey;

//import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截请求，AOP实现，获取request header
 */
@Aspect
@Component
public class RequestAspect {

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.kennedy.apipassenge.controller..*Controller*.*(..))")
    private void anyMehtod(){

    }

    /**
     * 在之前切入
     * 此时IDEA中左侧栏能看到被拦截的方法
     * @param joinPoint
     */
    @Before(value = "anyMehtod()")
    public void before(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String version = request.getHeader("version");

        Map<String,String> map = new HashMap<>();
        map.put("version",version);

        RibbonParameters.set(map);  //写入ThreadLocal

        // 使用 io.jmnarloch 实现，pom中打开被注释的地方
        //灰度规则 匹配的地方 查db, redis
//        if (version.trim().equals("v1")) {
//            RibbonFilterContextHolder.getCurrentContext().add("version", "v1");
//        } else if (version.trim().equals("v2")){
//            RibbonFilterContextHolder.getCurrentContext().add("version", "v2");
//        }
    }
}
