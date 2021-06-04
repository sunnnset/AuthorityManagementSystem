package com.xy.ams.backup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.xy.ams", exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class AmsBackupApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsBackupApplication.class, args);
    }

}
