package com.xy.ams.admin.service;

import com.xy.ams.admin.dao.SysDeptMapper;
import com.xy.ams.admin.model.SysDept;
import com.xy.ams.core.service.CurdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SysDeptService extends CurdService<SysDept> {

    /**
     * 获取机构树
     * @return 机构树
     */
    List<SysDept> findTree();

}
