package com.kennedy.apipassenge.ribbonconfig;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义负载规则
 */
public class KennedyRandomRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        return choose(getLoadBalancer(), o);
    }

    /**
     * 重载choose() 通过 LB 取得 server
     * @param lb
     * @param o
     * @return
     */
    public Server choose(ILoadBalancer lb, Object o) {
        System.out.println("KennedyRandomRule 自定义rule");

        if (lb == null) {
            return null;
        }

        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }

            List<Server> upList = lb.getReachableServers();  //激活可用的服务
            List<Server> allList = lb.getAllServers();  //所有的服务

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            //选自定义元数据的server，选择端口以2结尾的服务。
            for (int i = 0; i < upList.size(); i++) {
                server = upList.get(i);
                String port = server.getHostPort();
                if(port.endsWith("2")) {
                    break;
                }
            }

            if (server == null) {
                Thread.yield();
                continue;
            }
            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }
        return server;
    }
}
