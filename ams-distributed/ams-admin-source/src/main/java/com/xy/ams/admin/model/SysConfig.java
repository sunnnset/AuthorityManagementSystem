package com.xy.ams.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig {
    private Long id;

    private String value;

    private String label;

    private String type;

    private String description;

    private Long sort;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private String remarks;

    private Byte delFlag;
}