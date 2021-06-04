package com.xy.amsd.admin.controller;

import com.xy.amsd.admin.model.SysLoginLog;
import com.xy.amsd.admin.service.SysLoginLogService;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('sys:loginlog:view')")
    @PostMapping(value = "findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysLoginLogService.findPage(pageRequest));
    }

    @PreAuthorize("hasAuthority('sys:loginlog:delete')")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysLoginLog> records) {
        return HttpResult.ok(sysLoginLogService.delete(records));
    }
}
