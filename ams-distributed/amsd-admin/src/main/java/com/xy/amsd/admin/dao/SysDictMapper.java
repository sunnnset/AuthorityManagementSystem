package com.xy.amsd.admin.dao;

import com.xy.amsd.admin.model.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    List<SysDict> findPage();

    List<SysDict> findByLabel(@Param(value = "label") String label);

    List<SysDict> findPageByLabel(@Param(value = "label") String label);
}