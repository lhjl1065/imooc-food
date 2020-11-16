package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.UserCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserCenterController  用户中心接口
 *
 * @author linHu daXia
 * @date 2020/11/15 21:27
 */
@Api(value = "用户中心调用的接口",tags = {"用户中心调用的接口"})
@RestController
@RequestMapping("/center")
public class UserCenterController {
    @Autowired
    private UserCenterService userCenterService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "查询用户信息接口",notes = "查询用户信息接口",httpMethod = "GET")
    public IMOOCJSONResult userInfo(@RequestParam String userId){
        UserInfoVo userInfo = userCenterService.getUserInfo(userId);
        return IMOOCJSONResult.ok(userInfo);
    }
}
