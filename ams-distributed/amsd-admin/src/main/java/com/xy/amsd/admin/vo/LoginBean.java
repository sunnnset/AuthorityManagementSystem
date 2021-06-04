package com.xy.amsd.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录接口封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginBean {

    private String userName;
    private String password;
    private String captcha;
}
