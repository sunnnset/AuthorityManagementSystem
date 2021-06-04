package com.xy.ams.admin.dao;

import com.xy.ams.admin.model.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);

    List<SysRoleMenu> findAll();

    List<SysRoleMenu> findByRoleId(@Param(value = "roleId") Long roleId);

    int deleteByRoleId(@Param(value = "roleId") Long roleId);
}