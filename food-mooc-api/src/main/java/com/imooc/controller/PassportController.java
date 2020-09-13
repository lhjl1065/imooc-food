package com.imooc.controller;


import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public Object usernameIsExist(String name){
        if (StringUtils.isBlank(name)) {
            return IMOOCJSONResult.errorMsg("用户名为空");
        }
        boolean flag = userService.queryUsernameIsExist(name);
        if (flag){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }
}
