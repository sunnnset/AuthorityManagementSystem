package com.xy.amsd.zuul.security;

import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class SysUserServiceImpl {

    @Autowired
    FeignUserService feignUserService;

    public SysUser findByName(String userName) {
        HttpResult httpResult = feignUserService.findByName(userName);

        return null;
    }

    public Set<String> findUserPermissions(String userName) {
        HttpResult httpResult = feignUserService.findUserPermissions(userName);
        return null;
    }

}
