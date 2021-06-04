package com.xy.ams.admin.dao;

import com.xy.ams.admin.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * 根据用户id查找对应的
     * @param userId
     * @return
     */
    List<SysUserRole> findByUserId(@Param(value = "userId") Long userId);

    int deleteByUserId(@Param(value = "userId") Long userId);
}