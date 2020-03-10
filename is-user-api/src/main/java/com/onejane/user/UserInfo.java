package com.onejane.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 封装服务请求和响应
 */
@Data
public class UserInfo {

    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    //权限
    private String permissions;
}
