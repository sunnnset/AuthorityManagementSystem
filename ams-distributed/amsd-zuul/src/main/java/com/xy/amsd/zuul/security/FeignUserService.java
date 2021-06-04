package com.xy.amsd.zuul.security;

import com.xy.amsd.core.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "amsd-urmservice")
public interface FeignUserService {

    @RequestMapping("/user/findByName")
    HttpResult findByName(@RequestBody String name);

    @RequestMapping("/user/findUserPermissions")
    HttpResult findUserPermissions(@RequestBody String name);
}
