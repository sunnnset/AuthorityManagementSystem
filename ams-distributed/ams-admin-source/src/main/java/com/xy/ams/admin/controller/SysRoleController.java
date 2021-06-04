package com.xy.ams.admin.controller;

import com.xy.ams.admin.constant.SysConstants;
import com.xy.ams.admin.model.SysRole;
import com.xy.ams.admin.model.SysRoleMenu;
import com.xy.ams.admin.service.SysRoleService;
import com.xy.ams.core.http.HttpResult;
import com.xy.ams.core.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 保存角色信息
     * @param record SysRole类型，用户角色
     * @return ()
     */
    @PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
    @PostMapping(value = "save")
    public HttpResult save(@RequestBody SysRole record) {
        SysRole role = sysRoleService.findById(record.getId());
        // 修改角色
        if (role != null) {
            if (SysConstants.ADMIN.equals(role.getName())) {
                return HttpResult.error("禁止修改超级管理员角色");
            }
            return HttpResult.ok(sysRoleService.save(record));
        }
        if ((record.getId() == null || record.getId() == 0) && sysRoleService.findByName(record.getName()) != null) {
            return HttpResult.error("角色名已存在");
        }
        // 新增角色
        return HttpResult.ok(sysRoleService.save(record));
    }

    /**
     * 删除角色
     * @param records 要删除的角色
     * @return ( )
     */
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping(value = "delete")
    public HttpResult delete(@RequestBody List<SysRole> records) {
        return HttpResult.ok(sysRoleService.delete(records));
    }

    /**
     * 分页查询所有角色
     * @param pageRequest 分页请求
     * @return SysRole列表
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysRoleService.findPage(pageRequest));
    }

    /**
     * 查询所有角色
     * @return 所有SysRole的列表
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "findAll")
    public HttpResult findAll() {
        return HttpResult.ok(sysRoleService.findAll());
    }

    /**
     * 根据角色的id(roleId)找到对应的菜单项
     * @param roleId 角色Id
     * @return 菜单的列表List<SysMenu>
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam Long roleId) {
        return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
    }

    /**
     * 保存某个角色的菜单权限（可访问的菜单项）
     * @param records SysRoleMenu列表，角色与菜单项的对应关系
     * @return ( )
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping(value = "saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
        Long adminRoleId = sysRoleService.findByName(SysConstants.ADMIN).get(0).getId();
        for (SysRoleMenu record : records) {
            if (adminRoleId.equals(record.getRoleId())) {
                return HttpResult.error("无法修改超级管理员的菜单权限");
            }
        }
        return HttpResult.ok(sysRoleService.saveRoleMenus(records));
    }
}
