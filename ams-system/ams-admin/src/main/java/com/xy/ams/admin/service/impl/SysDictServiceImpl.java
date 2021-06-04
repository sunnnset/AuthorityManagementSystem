package com.xy.ams.admin.service.impl;

import com.xy.ams.admin.dao.SysDictMapper;
import com.xy.ams.admin.model.SysDict;
import com.xy.ams.admin.service.SysDictService;
import com.xy.ams.core.page.MyBatisPageHelper;
import com.xy.ams.core.page.PageRequest;
import com.xy.ams.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> findByLabel(String label) {
        return sysDictMapper.findByLabel(label);
    }

    @Override
    public int save(SysDict record) {
        // 如果id为空或为0，则为插入新记录；否则为更新记录
        if (record.getId() == null || record.getId() == 0) {
            return sysDictMapper.insertSelective(record);
        }
        return sysDictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDict record) {
        return sysDictMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDict> records) {
        for(SysDict record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysDict findById(Long id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        if (label != null) {
            return MyBatisPageHelper.findPage(pageRequest, sysDictMapper, "findPageByLabel", label);
        }
        return MyBatisPageHelper.findPage(pageRequest, sysDictMapper);
    }

}
