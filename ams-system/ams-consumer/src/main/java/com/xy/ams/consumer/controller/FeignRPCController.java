package com.xy.ams.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xy.ams.consumer.feignService.AMSProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignRPCController {

    @Autowired
    private AMSProducerService amsProducerService;

    @RequestMapping("feign/call")
    public String call() {
        // 调用接口时，Feign会通过动态代理调用远程方法
        return amsProducerService.helloRPC();
    }

    public String fallback() {
        System.out.println("fallback");
        return "Service unavailable now.";
    }
}
