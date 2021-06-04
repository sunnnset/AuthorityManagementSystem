package com.xy.amsd.auxservice.controller;

import com.xy.amsd.auxservice.service.SysConfigService;
import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.model.SysConfig;
import com.xy.amsd.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @Role({"sys:config:add", "sys:config:edit"})
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysConfig record) {
        return HttpResult.ok(sysConfigService.save(record));
    }

    @Role("sys:config:delete")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysConfig>records) {
        return HttpResult.ok(sysConfigService.delete(records));
    }

    @Role("sys:config:view")
    @PostMapping(value = "findByPage")
    public HttpResult findByPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysConfigService.findPage(pageRequest));
    }

    @Role("sys:config:view")
    @GetMapping(value = "findByLabel")
    public HttpResult findByLabel(@RequestParam String label) {
        return HttpResult.ok(sysConfigService.findByLabel(label));
    }
}
