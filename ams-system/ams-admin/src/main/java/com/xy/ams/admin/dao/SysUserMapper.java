package com.xy.ams.admin.dao;

import com.xy.ams.admin.model.SysUser;
import com.xy.ams.core.page.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> findAll();

    List<SysUser> findPage();

    SysUser findByName(@Param(value = "name") String name);
}