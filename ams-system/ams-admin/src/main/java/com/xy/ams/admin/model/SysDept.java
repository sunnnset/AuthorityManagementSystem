package com.xy.ams.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDept {
    private Long id;

    private String name;

    private Long parentId;

    private Integer orderNum;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;

    private int deptLevel;  // 非数据库字段，该部门的级别

    private List<SysDept> childDepts; // 非数据库字段，子部门

    private String parentName;


    public SysDept(Long id, String name, Long parentId, Integer orderNum, String createBy, Date createTime, String lastUpdateBy, Date lastUpdateTime, Byte delFlag) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.orderNum = orderNum;
        this.createBy = createBy;
        this.createTime = createTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.delFlag = delFlag;
        this.deptLevel = deptLevel;
        this.childDepts = childDepts;
        this.parentName = parentName;
    }
}