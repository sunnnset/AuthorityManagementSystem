package com.xy.amsd.admin.service.impl;

import com.xy.amsd.admin.dao.SysDeptMapper;
import com.xy.amsd.admin.model.SysDept;
import com.xy.amsd.admin.service.SysDeptService;
import com.xy.amsd.core.page.MyBatisPageHelper;
import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public int save(SysDept record) {
        if (record.getId() != null || record.getId() != 0) {
            return sysDeptMapper.updateByPrimaryKeySelective(record);
        } else {
            return sysDeptMapper.insertSelective(record);
        }
    }

    @Override
    public int delete(SysDept record) {
        return sysDeptMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDept> records) {
        for (SysDept record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysDept findById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyBatisPageHelper.findPage(pageRequest, sysDeptMapper);
    }

    @Override
    public List<SysDept> findTree() {
        List<SysDept> parentDepts = new ArrayList<>();
        List<SysDept> depts = sysDeptMapper.findAll();
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                dept.setDeptLevel(0);
                parentDepts.add(dept);
            }
        }
        findChildren(parentDepts, depts);
        return parentDepts;
    }

    private void findChildren(List<SysDept> parentDepts, List<SysDept> depts) {
        for (SysDept parentDept : parentDepts) {
            List<SysDept> childDepts = new ArrayList<>();
            for (SysDept dept : depts) {
                if (parentDept.getId() != null && parentDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(dept.getName());
                    dept.setDeptLevel(parentDept.getDeptLevel() + 1);
                    childDepts.add(dept);
                }
            }
            parentDept.setChildDepts(childDepts);
            // 递归查找并设置子部门的子部门
            // TODO: 尝试在递归前从depts中删除本部门(parentDept)？
            findChildren(childDepts, depts);
        }
    }
}
