package com.xy.amsd.admin.service;

import com.xy.amsd.admin.model.SysConfig;
import com.xy.amsd.core.service.CurdService;

import java.util.List;

public interface SysConfigService extends CurdService<SysConfig> {

    /**
     * 根据label查找config
     * @param label 标签
     * @return config
     */
    List<SysConfig> findByLabel(String label);
}
