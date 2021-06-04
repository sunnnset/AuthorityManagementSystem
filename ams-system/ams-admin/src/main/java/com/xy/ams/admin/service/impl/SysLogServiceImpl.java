package com.xy.ams.admin.service.impl;

import com.xy.ams.admin.dao.SysLogMapper;
import com.xy.ams.admin.model.SysLog;
import com.xy.ams.admin.model.SysLoginLog;
import com.xy.ams.admin.service.SysLogService;
import com.xy.ams.core.page.MyBatisPageHelper;
import com.xy.ams.core.page.PageRequest;
import com.xy.ams.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public int save(SysLog record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysLogMapper.insertSelective(record);
        } else {
            return sysLogMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public int delete(SysLog record) {
        return sysLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysLog> records) {
        for (SysLog record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysLog findById(Long id) {
        return sysLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object userName = pageRequest.getParam("userName");
        if (userName != null) {
            return MyBatisPageHelper.findPage(pageRequest, sysLogMapper, "findPageByUserName", userName);
        } else {
            return MyBatisPageHelper.findPage(pageRequest, sysLogMapper);
        }

    }
}
