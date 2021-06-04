package com.xy.amsd.auxservice.service;



import com.xy.amsd.core.model.SysDict;
import com.xy.amsd.core.service.CurdService;

import java.util.List;

public interface SysDictService extends CurdService<SysDict> {

    /**
     * 根据标签查找dict
     * @param label 标签
     * @return dict列表
     */
    List<SysDict> findByLabel(String label);
}
