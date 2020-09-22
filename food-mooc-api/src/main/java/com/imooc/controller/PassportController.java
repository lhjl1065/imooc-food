package com.imooc.controller;


import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.JsonUtils;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "注册登录", tags = {"用于注册登录的接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用于验证用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public Object usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名为空");
        }
        boolean flag = userService.queryUsernameIsExist(username);
        if (flag) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册账户", notes = "用于注册账号", httpMethod = "POST")
    public Object register(@RequestBody UserBo userBo) {
        //获取前端页面传来的三个值
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String checkPassword = userBo.getconfirmPassword();
        //如果有一个为空，那么返回500，用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(checkPassword)) {
            return IMOOCJSONResult.errorMsg("用户名和密码不能为空");
        }
        //如果有用户名已经存在,返回500,用户名已经存在
        boolean flag = userService.queryUsernameIsExist(username);
        if (flag) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //密码小于6位,返回500,密码不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码不能少于6位");
        }
        //如果两次密码不一致,返回500,两次输入密码不一致
        if (!password.equals(checkPassword)) {
            return IMOOCJSONResult.errorMsg("两次输入密码不一致");
        }
        //满足所有条件,存入数据库
        userService.createUser(userBo);
        return IMOOCJSONResult.ok();
    }

    /**
     * 用于登录的接口
     * @param userBo 登录页面接收的账号和密码
     * @return 用户对象
     */
    @ApiOperation(value = "用户登录",notes = "用户登录的接口",httpMethod = "POST")
    @PostMapping("/login")
    public Object login(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response) {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return IMOOCJSONResult.errorMsg("用户名和密码不能为空");
        }
        Users userResult = userService.login(userBo);
        if (userResult==null){
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }
        //把user中的隐私信息值设为空
        setNull(userResult);
        //把user放到response中返回
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);
        return IMOOCJSONResult.ok(userResult);

    }
    @ApiOperation(value = "用户退出",notes = "用户退出",httpMethod = "POST")
    @PostMapping("/logout")
    public Object logout(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
        //删除登录相关的cookie
        CookieUtils.deleteCookie(request,response,"user");
        return IMOOCJSONResult.ok();
    }
    private void setNull(Users userResult){
        userResult.setPassword(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setEmail(null);
        userResult.setMobile(null);
        userResult.setBirthday(null);
    }
}
