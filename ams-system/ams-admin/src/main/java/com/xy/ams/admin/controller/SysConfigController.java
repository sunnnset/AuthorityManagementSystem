package com.xy.ams.admin.controller;

import com.xy.ams.admin.model.SysConfig;
import com.xy.ams.admin.service.SysConfigService;
import com.xy.ams.core.http.HttpResult;
import com.xy.ams.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @PreAuthorize("hasAuthority('sys:config:add') AND hasAuthority('sys:config:edit')")
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysConfig record) {
        return HttpResult.ok(sysConfigService.save(record));
    }

    @PreAuthorize("hasAuthority('sys:config:delete')")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysConfig>records) {
        return HttpResult.ok(sysConfigService.delete(records));
    }

    @PreAuthorize("hasAuthority('sys:config:view')")
    @PostMapping(value = "findByPage")
    public HttpResult findByPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysConfigService.findPage(pageRequest));
    }

    @PreAuthorize("hasAuthority('sys:config:view')")
    @GetMapping(value = "findByLabel")
    public HttpResult findByLabel(@RequestParam String label) {
        return HttpResult.ok(sysConfigService.findByLabel(label));
    }
}
