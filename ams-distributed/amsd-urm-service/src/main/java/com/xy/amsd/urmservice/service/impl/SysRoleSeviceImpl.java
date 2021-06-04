package com.xy.amsd.urmservice.service.impl;

import com.xy.amsd.urmservice.constant.SysConstants;
import com.xy.amsd.urmservice.dao.SysMenuMapper;
import com.xy.amsd.urmservice.dao.SysRoleMapper;
import com.xy.amsd.urmservice.dao.SysRoleMenuMapper;
import com.xy.amsd.core.model.SysMenu;
import com.xy.amsd.core.model.SysRole;
import com.xy.amsd.core.model.SysRoleMenu;
import com.xy.amsd.urmservice.service.SysRoleService;
import com.xy.amsd.core.page.MyBatisPageHelper;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleSeviceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public List<SysRole> findByName(String name) {
        return sysRoleMapper.findByName(name);
    }

    /**
     * 根据roleId查询对应角色的菜单
     * @param roleId 角色Id
     * @return 菜单
     */
    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (SysConstants.ADMIN.equals(sysRole.getName())) {
            return sysMenuMapper.findAll();
        } else {
            return sysMenuMapper.findByRoleId(roleId);
        }
    }

    @Transactional
    @Override
    public int saveRoleMenus(List<SysRoleMenu> records) {
        if (records == null || records.isEmpty()) {
            return 1;
        }
        Long roleId = records.get(0).getRoleId();
        // 先删除角色的已有菜单，再插入新菜单
        sysRoleMenuMapper.deleteByRoleId(roleId);
        for (SysRoleMenu record : records) {
            sysRoleMenuMapper.insertSelective(record);
        }
        return 1;
    }

    @Override
    public int save(SysRole record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysRoleMapper.insertSelective(record);
        } else {
            return sysRoleMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public int delete(SysRole record) {
        return sysRoleMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysRole> records) {
        for (SysRole record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("name");
        if (label != null) {
            return MyBatisPageHelper.findPage(pageRequest, sysRoleMapper, "findPageByName", label);
        } else {
            return MyBatisPageHelper.findPage(pageRequest, sysRoleMapper);
        }
    }
}
