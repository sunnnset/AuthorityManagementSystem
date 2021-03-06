package com.xy.ams.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AmsProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsProducerApplication.class, args);
    }

}
