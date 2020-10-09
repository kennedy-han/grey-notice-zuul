package com.kennedy.apipassenge.grey;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

/**
 * 自定义Ribbon配置，用于启动类
 */
public class GrayRibbonConfiguration {

    @Bean
    public IRule ribbonRule(){
        return new GreyRule();
    }
}
