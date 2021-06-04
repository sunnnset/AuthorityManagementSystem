package com.xy.ams.admin.service;

import com.xy.ams.admin.model.SysDict;
import com.xy.ams.core.service.CurdService;

import java.util.List;

public interface SysDictService extends CurdService<SysDict> {

    /**
     * 根据标签查找dict
     * @param label 标签
     * @return dict列表
     */
    List<SysDict> findByLabel(String label);
}
