package com.xy.ams.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {
    private Long id;

    private String name;

    private Long parentId;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;

    // 非数据库字段
    private String parentName;

    // 非数据库字段
    private Integer menuLevel;

    // 非数据库字段
    private List<SysMenu> childMenus;

    public SysMenu(Long id, String name, Long parentId, String url, String perms, Integer type, String icon, Integer orderNum, String createBy, Date createTime, String lastUpdateBy, Date lastUpdateTime, Byte delFlag) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.perms = perms;
        this.type = type;
        this.icon = icon;
        this.orderNum = orderNum;
        this.createBy = createBy;
        this.createTime = createTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.delFlag = delFlag;
    }
}