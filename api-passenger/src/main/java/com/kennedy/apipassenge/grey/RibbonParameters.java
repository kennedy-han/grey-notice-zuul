package com.kennedy.apipassenge.grey;

import org.springframework.stereotype.Component;

/**
 * 用于 保存、获取 每个线程中的 request header
 */
@Component
public class RibbonParameters {

    private static final ThreadLocal local = new ThreadLocal();

    public static <T> T get(){
        return (T)local.get();
    }

    public static <T> void set(T t){
        local.set(t);
    }
}
