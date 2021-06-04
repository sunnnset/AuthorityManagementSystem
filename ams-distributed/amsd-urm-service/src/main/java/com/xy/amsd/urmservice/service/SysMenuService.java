package com.xy.amsd.urmservice.service;

import com.xy.amsd.core.model.SysMenu;
import com.xy.amsd.core.service.CurdService;

import java.util.List;

public interface SysMenuService extends CurdService<SysMenu> {

    /**
     * 根据用户名和菜单类型查询菜单树
     * @param userName 用户名
     * @param menuType 菜单类型：0 - 所有菜单  1 - 除按钮外的菜单
     * @return 菜单树
     */
    List<SysMenu> findTree(String userName, int menuType);

    /**
     * 根据用户名查询对应角色组的菜单列表
     * @param userName 用户名
     * @return 菜单列表
     */
    List<SysMenu> findByUserName(String userName);

    /**
     * 查找全部菜单
     * @return 菜单列表
     */
    List<SysMenu> findAll();
}
