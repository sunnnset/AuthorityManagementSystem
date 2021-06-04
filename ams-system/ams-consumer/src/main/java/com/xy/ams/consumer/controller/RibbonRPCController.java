package com.xy.ams.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonRPCController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/ribbon/call")
    public String call() {
        // 由于在启动类中设置了RestTemplate的负载均衡注解，这里会隐式使用负载均衡
        String callServiceResult = restTemplate.getForObject("http://ams-producer/helloRPC", String.class);
        return callServiceResult;
    }
}
