package com.xy.amsd.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private Long id;

    private String name;

    private String nickName;

    private String avatar;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;

    // 非数据库字段
    private String deptName;

    // 非数据库字段
    private String roleName;

    // 非数据库字段
    private List<SysUserRole> userRoles = new ArrayList<>();

    public SysUser(Long id, String name, String nickName, String avatar, String password, String salt, String email, String mobile, Byte status, Long deptId, String createBy, Date createTime, String lastUpdateBy, Date lastUpdateTime, Byte delFlag) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.avatar = avatar;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.deptId = deptId;
        this.createBy = createBy;
        this.createTime = createTime;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateTime = lastUpdateTime;
        this.delFlag = delFlag;
    }
}