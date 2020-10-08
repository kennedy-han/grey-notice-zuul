package com.kennedy.cloudzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 灰度发布 Filter
 */
@Component
public class GreyFilter extends ZuulFilter {

    /**
     * 注意这里的 filter type 类型为 ROUTE_TYPE，路由过程中的
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;    //打开
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        //这里获取的是request header中 userId
        //也可以使用 token等自定义请求头
        //根据token解析用户，然后根据用户规则表，找到对应的metadata
        int userId = Integer.parseInt(request.getHeader("userId"));

        // 根据用户id 查 规则  可以是查库 v1,meata 也可以查Redis 或者本地缓存
        // ...这里简略
        // 数据库表可以这样设计： id | user_id | service_name | meta_version
        //这里只是简单实现了转发规则，userId == 1的 转发到 metadata version = v1的服务

        // 金丝雀（灰度发布）
        if (userId == 1){
            //这里的 version 和 v1 信息，是配置在 service-sms项目 eureka metadata中
            RibbonFilterContextHolder.getCurrentContext().add("version","v1");
            // 普通用户
        }else if (userId == 2){
            RibbonFilterContextHolder.getCurrentContext().add("version","v2");
        }

        return null;
    }
}
