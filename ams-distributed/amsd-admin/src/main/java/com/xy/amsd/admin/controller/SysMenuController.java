package com.xy.amsd.admin.controller;

import com.xy.amsd.admin.model.SysMenu;
import com.xy.amsd.admin.service.SysMenuService;
import com.xy.amsd.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('sys:menu:add') AND hasAuthority('sys:menu:edit')")
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysMenu record) {
        return HttpResult.ok(sysMenuService.save(record));
    }

    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysMenu> records) {
        return HttpResult.ok(sysMenuService.delete(records));
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "findMenuTree")
    public HttpResult findMenuTree() {
        return HttpResult.ok(sysMenuService.findAll());
    }

    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping(value = "findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        return HttpResult.ok(sysMenuService.findTree(userName, 1));
    }

}
