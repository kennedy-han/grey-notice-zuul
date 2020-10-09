package com.kennedy.apipassenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 用于服务与服务之间的灰度发布测试
 */
@RestController
@RequestMapping("/test")
public class TestCallServiceSmsController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 该方法会调用 service-sms 中的方法
     * @return
     */
    @GetMapping("/call")
    public String testCall(){

        return restTemplate.getForObject("http://service-sms/test/sms-test",String.class);
    }

    @GetMapping("/test")
    public String testCall1(){

        return "api-passenger";
    }
}
