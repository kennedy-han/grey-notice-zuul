package com.kennedy.apipassenge;

import com.kennedy.apipassenge.annotation.ExcludeRibbonConfig;
import com.kennedy.apipassenge.grey.GrayRibbonConfiguration;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION, value= ExcludeRibbonConfig.class)
        }
)
//@RibbonClient(name = "service-sms" , configuration = GrayRibbonConfiguration.class)
public class ApiPassengeApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiPassengeApplication.class, args);
    }

}
