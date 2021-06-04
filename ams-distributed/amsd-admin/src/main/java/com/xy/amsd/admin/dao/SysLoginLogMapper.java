package com.xy.amsd.admin.dao;

import com.xy.amsd.admin.model.SysLog;
import com.xy.amsd.admin.model.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginLog record);

    int insertSelective(SysLoginLog record);

    SysLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginLog record);

    int updateByPrimaryKey(SysLoginLog record);

    List<SysLog> findPage();

    List<SysLog> findPageByUserName(@Param(value = "userName") String userName);

    List<SysLog> findPageByStatus(@Param(value = "status") String status);
}