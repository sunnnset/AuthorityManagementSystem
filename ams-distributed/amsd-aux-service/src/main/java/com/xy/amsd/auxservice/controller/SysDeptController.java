package com.xy.amsd.auxservice.controller;


import com.xy.amsd.auxservice.service.SysDeptService;
import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.model.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    // @PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
    @Role({"sys:dept:add", "sys:dept:edit"})
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysDept record) {
        return HttpResult.ok(sysDeptService.save(record));
    }

    // @PreAuthorize("hasAuthority('sys:dept:delete')")
    @Role("sys:dept:delete")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysDept> records) {
        return HttpResult.ok(sysDeptService.delete(records));
    }

    // @PreAuthorize("hasAuthority('sys:dept:view')")
    @Role("sys:dept:view")
    @GetMapping(value = "findTree")
    public HttpResult findTree() {
        return HttpResult.ok(sysDeptService.findTree());
    }
}
