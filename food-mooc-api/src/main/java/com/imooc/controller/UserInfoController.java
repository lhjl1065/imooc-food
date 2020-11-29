package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.bo.UserInfoBo;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.UserCenterService;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserInfoCotroller TODO
 *
 * @author linHu daXia
 * @date 2020/11/22 17:53
 */
@RestController
@RequestMapping("/userInfo")
@Api(value = "用户中心接口",tags = "用户中心接口")
public class UserInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserCenterService userCenterService;

    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息接口",notes = "更新用户信息接口",httpMethod = "POST")
    public IMOOCJSONResult updateUserInfo(
        @RequestParam @ApiParam(value = "用户id",required = true,example = "200924GA7FBNTSCH") String userId,
        @Valid @RequestBody UserInfoBo userInfoBo,
        BindingResult result,
        HttpServletRequest request,
        HttpServletResponse response){
        //验证参数
        Map<String, String> errorMap = getErrorMap(result);
        if (errorMap.size()>=1){
            return IMOOCJSONResult.errorMap(errorMap);
        }
        UserInfoVo userInfoVo=userCenterService.updateUserInfo(userInfoBo,userId,request,response);
        return IMOOCJSONResult.ok(userInfoVo);
    }
    private Map<String, String> getErrorMap(BindingResult result){
        HashMap<String, String> errorMessageResult = new HashMap<>();
        for (FieldError error:result.getFieldErrors()){
            errorMessageResult.put(error.getField(),error.getDefaultMessage());
        }
        return errorMessageResult;
    }
}
