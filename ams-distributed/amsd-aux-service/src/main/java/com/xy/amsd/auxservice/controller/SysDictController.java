package com.xy.amsd.auxservice.controller;

import com.xy.amsd.auxservice.service.SysDictService;
import com.xy.amsd.core.auth.Role;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.model.SysDict;
import com.xy.amsd.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @Role({"sys:dict:add", "sys:dict:edit"})
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysDict record) {
        return HttpResult.ok(sysDictService.save(record));
    }

    @Role("sys:dict:delete")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysDict> records) {
        return HttpResult.ok(sysDictService.delete(records));
    }

    @Role("sys:dict:view")
    @PostMapping(value = "findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }

    @Role("sys:dict:view")
    @GetMapping(value = "findByLabel")
    public HttpResult findByLabel(@RequestParam String label) {
        return HttpResult.ok(sysDictService.findByLabel(label));
    }
}
