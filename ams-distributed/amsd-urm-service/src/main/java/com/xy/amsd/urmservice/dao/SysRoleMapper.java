package com.xy.amsd.urmservice.dao;

import com.xy.amsd.core.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> findPage();

    List<SysRole> findAll();

    List<SysRole> findByName(@Param(value = "name") String name);

    // 模糊查询
    List<SysRole> findPageByName(@Param(value = "name") String name);
}