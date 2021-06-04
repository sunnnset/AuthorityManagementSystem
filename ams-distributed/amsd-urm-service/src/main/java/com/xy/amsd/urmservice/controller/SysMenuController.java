package com.xy.amsd.urmservice.controller;

import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.model.SysMenu;
import com.xy.amsd.urmservice.service.SysMenuService;
import com.xy.amsd.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Role({"sys:menu:add", "sys:menu:edit"})
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysMenu record) {
        return HttpResult.ok(sysMenuService.save(record));
    }

    @Role({"sys:menu:detele"})
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysMenu> records) {
        return HttpResult.ok(sysMenuService.delete(records));
    }

    @Role({"sys:menu:view"})
    @GetMapping(value = "findMenuTree")
    public HttpResult findMenuTree() {
        return HttpResult.ok(sysMenuService.findAll());
    }

    @Role({"sys:menu:view"})
    @GetMapping(value = "findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        return HttpResult.ok(sysMenuService.findTree(userName, 1));
    }

}
