package com.xy.ams.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RPCDemoController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    /**
     * 一个远程调用的demo
     * @return
     */
    @RequestMapping(value = "call")
    public String call() {
        // 通过负载均衡器查找服务提供者
        ServiceInstance serviceInstance = loadBalancer.choose("ams-producer");
        System.out.println("服务地址："+serviceInstance.getUri());
        System.out.println("服务名称："+serviceInstance.getServiceId());
        // 通过RestTemplate.getForObject()进行远程调用
        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/helloRPC", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }
}
