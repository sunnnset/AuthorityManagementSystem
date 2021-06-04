package com.xy.ams.consumer.feignService;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class AMSFallbackService implements AMSProducerService{

    @Override
    @RequestMapping(value = "helloRPC")
    public String helloRPC() {
        return "sorry, helloRPC service call failed.";
    }
}
