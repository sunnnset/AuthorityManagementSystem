package com.xy.amsd.urmservice.service;

import com.xy.amsd.core.model.SysUser;
import com.xy.amsd.core.model.SysUserRole;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.service.CurdService;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface SysUserService extends CurdService<SysUser> {

    /**
     * 获取所有User的列表
     * @return 如上所述
     */
    List<SysUser> findAll();

    /**
     * 根据用户名查找user
     * @param name 用户名
     * @return user
     */
    SysUser findByName(String name);

    /**
     * 根据用户id查找user
     * @param id 用户id
     * @return user
     */
    SysUser findById(Long id);

    /**
     * 根据分页请求查询用户列表，然后将列表转换成Excel表格
     * @param pageRequest 分页请求
     * @return Excel文件
     */
    File createUserExcelFile(PageRequest pageRequest);

    /**
     * 根据用户id查找用户的角色列表
     * @param userId 用户id
     * @return 用户与角色的对应关系SysUserRole
     */
    List<SysUserRole> findUserRolesByUserId(Long userId);

    /**
     * 根据用户名查找用户的权限
     * @param userName 用户名
     * @return 用户权限集合
     */
    Set<String> findUserPermissions(String userName);
}
