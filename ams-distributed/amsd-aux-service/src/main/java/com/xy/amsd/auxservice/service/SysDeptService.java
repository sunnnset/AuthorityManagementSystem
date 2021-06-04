package com.xy.amsd.auxservice.service;


import com.xy.amsd.core.model.SysDept;
import com.xy.amsd.core.service.CurdService;

import java.util.List;

public interface SysDeptService extends CurdService<SysDept> {

    /**
     * 获取机构树
     * @return 机构树
     */
    List<SysDept> findTree();

}
