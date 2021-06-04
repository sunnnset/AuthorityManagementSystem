package com.xy.amsd.admin.service;

import com.xy.amsd.admin.model.SysDept;
import com.xy.amsd.core.service.CurdService;

import java.util.List;

public interface SysDeptService extends CurdService<SysDept> {

    /**
     * 获取机构树
     * @return 机构树
     */
    List<SysDept> findTree();

}
