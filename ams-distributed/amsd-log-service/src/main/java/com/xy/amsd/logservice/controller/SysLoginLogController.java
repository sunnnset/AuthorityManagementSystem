package com.xy.amsd.logservice.controller;


import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.model.SysLoginLog;
import com.xy.amsd.logservice.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "loginlog")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Role(value = {"sys:loginlog:view"})
    @PostMapping(value = "findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysLoginLogService.findPage(pageRequest));
    }

    @Role(value = {"sys:loginlog:delete"})
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysLoginLog> records) {
        return HttpResult.ok(sysLoginLogService.delete(records));
    }
}
