package com.xy.amsd.zuul;

import com.xy.amsd.zuul.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
public class AmsdZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsdZuulApplication.class, args);
    }

    @Bean
    public LoginFilter LoginFilter() {
        return new LoginFilter();
    }
}
