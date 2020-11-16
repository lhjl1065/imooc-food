package com.imooc.pojo.vo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * UserInfoVo 用户信息
 *
 * @author linHu daXia
 * @date 2020/11/15 22:09
 */
@Getter
@Setter
public class UserInfoVo {
    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private Date birthday;

}
