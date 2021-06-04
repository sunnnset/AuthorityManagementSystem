package com.xy.amsd.admin.dao;

import com.xy.amsd.admin.model.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysConfigMapper {
    int deleteByPrimaryKey(Long id); //

    SysConfig selectByPrimaryKey(Long id); //

    int updateByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfig record);

    int insert(SysConfig record); //

    int insertSelective(SysConfig record); //

    List<SysConfig> findPage();

    List<SysConfig> findPageByLabel(@Param(value = "label") String label);

    List<SysConfig> findByLabel(@Param(value = "label") String label);


}