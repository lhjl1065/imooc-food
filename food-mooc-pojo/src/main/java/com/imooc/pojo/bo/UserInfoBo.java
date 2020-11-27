package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * serInfoBo 用户信息bo类
 *
 * @author linHu daXia
 * @date 2020/11/22 17:25
 */
@Getter
@Setter
@ApiModel(value = "用户信息Bo类",description = "用于封装用户中心修改用户数据时候接收数据")
public class UserInfoBo {
    /**
     * 昵称 昵称
     */
    @NotBlank(message = "网名不能为空")
    @ApiModelProperty(value = "昵称",example = "新生")
    private String nickname;

    /**
     * 真实姓名
     */
    @Length(min = 1,max = 12,message = "真实姓名必须长度大于1小于12")
    @ApiModelProperty(value = "姓名",example = "令狐珺岚")
    private String realname;

    /**
     * 手机号 手机号
     */
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$",message = "电话号码必须符合格式")
    @Length(max = 11)
    @ApiModelProperty(value = "电话号码",example = "15519021091")
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    @Email(message = "邮箱必须符合格式")
    @ApiModelProperty(value = "邮箱",example = "106527001@qq")
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    @Range(min = 0,max = 2,message = "性别只能是0, 1, 2")
    @ApiModelProperty("性别")
    private Integer sex;

    /**
     * 生日 生日
     */
    @Past(message = "生日必须是一个过去的日期")
    @ApiModelProperty("生日")
    private Date birthday;
}
