package com.xy.ams.admin.service;

import com.xy.ams.admin.model.SysConfig;
import com.xy.ams.core.service.CurdService;

import java.util.List;

public interface SysConfigService extends CurdService<SysConfig> {

    /**
     * 根据label查找config
     * @param label 标签
     * @return config
     */
    List<SysConfig> findByLabel(String label);
}
