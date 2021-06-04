package com.xy.amsd.logservice.controller;


import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.model.SysLog;
import com.xy.amsd.logservice.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @Role(value = {"sys:log:view"})
    @PostMapping(value = "findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysLogService.findPage(pageRequest));
    }

    @Role(value = {"sys:log:delete"})
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysLog> records) {
        return HttpResult.ok(sysLogService.delete(records));
    }
}
