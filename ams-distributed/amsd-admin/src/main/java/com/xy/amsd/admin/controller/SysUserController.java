package com.xy.amsd.admin.controller;

import com.xy.amsd.admin.model.SysUser;
import com.xy.amsd.admin.service.SysUserService;
import com.xy.amsd.common.utils.FileUtils;
import com.xy.amsd.core.http.HttpResult;
import com.xy.amsd.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "findAll")
    public Object findAll() {
        return sysUserService.findAll();
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        // pageNum = 1时为第一页
        return HttpResult.ok(sysUserService.findPage(pageRequest));
    }

    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysUser record) {
        return HttpResult.ok(sysUserService.save(record));
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        return HttpResult.ok(sysUserService.delete(records));
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "findByName")
    public HttpResult findByName(@RequestBody String name) {
        System.out.println(name);
        return HttpResult.ok(sysUserService.findByName(name));
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value = "findById")
    public HttpResult findById(@RequestBody Long id) {
        return HttpResult.ok(sysUserService.findById(id));
    }

    @PostMapping(value = "exportUserTable")
    public void exportUserTable(@RequestBody PageRequest pageRequest, HttpServletResponse response) {
        File file = sysUserService.createUserExcelFile(pageRequest);
        try {
            FileUtils.downloadFile(response, file, file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "findUserPermissions")
    public HttpResult findUserPermissions(@RequestParam String userName) {
        return HttpResult.ok(sysUserService.findUserPermissions(userName));
    }
}
