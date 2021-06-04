package com.xy.ams.admin.service.impl;

import com.xy.ams.admin.constant.SysConstants;
import com.xy.ams.admin.dao.SysMenuMapper;
import com.xy.ams.admin.dao.SysRoleMapper;
import com.xy.ams.admin.dao.SysRoleMenuMapper;
import com.xy.ams.admin.model.SysMenu;
import com.xy.ams.admin.model.SysRole;
import com.xy.ams.admin.model.SysRoleMenu;
import com.xy.ams.admin.service.SysRoleService;
import com.xy.ams.core.http.HttpResult;
import com.xy.ams.core.page.MyBatisPageHelper;
import com.xy.ams.core.page.PageRequest;
import com.xy.ams.core.page.PageResult;
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
