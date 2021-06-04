package com.xy.ams.admin.service;

import com.xy.ams.admin.model.SysMenu;
import com.xy.ams.admin.model.SysRole;
import com.xy.ams.admin.model.SysRoleMenu;
import com.xy.ams.admin.model.SysUserRole;
import com.xy.ams.core.service.CurdService;

import java.util.List;

public interface SysRoleService extends CurdService<SysRole> {

    /**
     * 获取所有角色的列表
     * @return 如上所述
     */
    List<SysRole> findAll();


    /**
     * 根据角色名称查找角色
     * @param name 角色名称
     * @return 角色
     */
    List<SysRole> findByName(String name);

    /**
     * 根据角色id查找角色的菜单
     * @param roleId 角色id
     * @return 角色的菜单列表
     */
    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色与菜单的对应关系
     * @param records SysRoleMenu列表
     * @return （ ）
     */
    int saveRoleMenus(List<SysRoleMenu> records);
}
