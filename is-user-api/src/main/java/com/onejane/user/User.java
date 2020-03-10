package com.onejane.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity  // 绑定表
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为Null") // 数据库 数据校验,sql执行之前校验，应用层面
    @Column(unique = true,nullable = true)  // 数据校验唯一，建立数据库层面校验
    private String username;

    private String password;

    //权限 自动在数据库生成字段，在数据库配置r rw相关权限
    private String permissions;

    public UserInfo buildInfo(){
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }

    public boolean hasPermission(String method) {
        boolean result = true;
        if(StringUtils.equalsAnyIgnoreCase("get",method)){
            result = StringUtils.contains(this.permissions,"r");
        }else{
            result = StringUtils.contains(this.permissions,"w");
        }
        return result;
    }
}
