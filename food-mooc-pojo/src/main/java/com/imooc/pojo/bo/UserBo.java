package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象bo",description = "用于封装注册页面数据")
public class UserBo {
    @ApiModelProperty(value = "用户名",example = "张三",required = true)
    private String username;
    @ApiModelProperty(value = "密码",example = "123456",required = true)
    private String password;
    @ApiModelProperty(value = "校验密码",example = "123456",required = false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getconfirmPassword() {
        return confirmPassword;
    }

    public void setconfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserBo() {
    }

    public UserBo(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserBo{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", confirmPassword='" + confirmPassword + '\'' +
            '}';
    }
}
