package com.xy.amsd.admin.service.impl;

import com.xy.amsd.admin.dao.SysLoginLogMapper;
import com.xy.amsd.admin.model.SysLoginLog;
import com.xy.amsd.admin.service.SysLoginLogService;
import com.xy.amsd.core.page.MyBatisPageHelper;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public int save(SysLoginLog record) {
        if (record.getId() == null || record.getId() == 0) {
            return sysLoginLogMapper.insertSelective(record);
        } else {
            return sysLoginLogMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public int delete(SysLoginLog record) {
        return sysLoginLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysLoginLog> records) {
        for (SysLoginLog record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysLoginLog findById(Long id) {
        return sysLoginLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        // 如果userName不为空，就按照用户名，分页查找LoginLog；
        Object userName = pageRequest.getParam("userName");
        if (userName != null) {
            return MyBatisPageHelper.findPage(pageRequest, sysLoginLogMapper, "findPageByUserName", userName);
        }
        // 如果status不为空，就按照状态，分页查找LoginLog；
        Object status = pageRequest.getParam("status");
        if (status != null) {
            return MyBatisPageHelper.findPage(pageRequest, sysLoginLogMapper, "findPageByStatus", status);
        }
        // 否则就分页查找全部LoginLog；
        return MyBatisPageHelper.findPage(pageRequest, sysLoginLogMapper);

    }
}
