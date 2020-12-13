package com.imooc.controller;

import com.imooc.common.enums.OrderStatusEnum;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.UserInfoVo;
import com.imooc.service.OrderService;
import com.imooc.service.UserCenterService;
import com.imooc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询用户订单接口
 */
@Api(value = "查询用户订单的接口",tags = {"查询用户订单的接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController {
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private OrderService orderService;

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

    @ApiOperation(value = "用户确认收货订单的接口",notes = "用户确认订单的接口",httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(@RequestParam String orderId,@RequestParam String userId) {
       // 检查订单和用户是否匹配
        boolean flag = orderService.checkOrderAndUserId(userId,orderId);
        if (!flag){
            return IMOOCJSONResult.errorMsg("用户和订单不匹配");
        }
        boolean flag2=orderService.confirmReceive(orderId);
        if (!flag2){
            return IMOOCJSONResult.errorMsg("更新状态为确认收货失败");
        }
        return IMOOCJSONResult.ok();

    }


    @ApiOperation(value = "用户删除关闭订单接口",notes = "用户删除关闭订单接口",httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(@RequestParam String orderId,@RequestParam String userId) {
        // 检查订单和用户是否匹配
        boolean flag = orderService.checkOrderAndUserId(userId,orderId);
        if (!flag){
            return IMOOCJSONResult.errorMsg("用户和订单不匹配");
        }

        // 检查订单状态是否关闭
        OrderStatus orderStatus = orderService.queryOrderStatus(orderId);
        if (!OrderStatusEnum.CLOSE.type.equals(orderStatus.getOrderStatus())){
            return IMOOCJSONResult.errorMsg("订单状态不为关闭");
        }
        // 删除订单
        orderService.delete(orderId);
        return IMOOCJSONResult.ok();

    }






}
