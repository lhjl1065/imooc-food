package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.UserCenterService;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询用户订单接口
 */
@Api(value = "查询用户订单的借口",tags = {"查询用户订单的接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController {
    @Autowired
    private UserCenterService userCenterService;

    @PostMapping("/query")
    @ApiOperation(value = "查询用户订单的接口",notes = "查询用户订单的接口",httpMethod = "POST")
    public IMOOCJSONResult query(
        @RequestParam Integer page,
        @RequestParam Integer pageSize,
        @RequestParam String userId,@RequestParam Integer orderStatus){
        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("用户id不能为空");
        }
        PagedGridResult pagedGridResult = userCenterService.queryUserOrder(page, pageSize, userId, orderStatus);
        return IMOOCJSONResult.ok(pagedGridResult);
    }





}
