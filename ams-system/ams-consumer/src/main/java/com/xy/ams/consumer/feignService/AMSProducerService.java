package com.xy.ams.consumer.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ams-producer", fallback = AMSFallbackService.class)
public interface AMSProducerService {

    @RequestMapping("helloRPC")
    public String helloRPC();
}
