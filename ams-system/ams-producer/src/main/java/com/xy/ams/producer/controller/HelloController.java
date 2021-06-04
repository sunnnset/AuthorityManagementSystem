package com.xy.ams.producer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "helloRPC")
    public String hello() {
        return "Hello RPC ! -2";
    }
}
