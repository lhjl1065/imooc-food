package com.imooc.controller;


import com.imooc.common.enums.YesOrNo;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.OrderItemBO;
import com.imooc.service.AddressService;
import com.imooc.service.MyCommentsService;
import com.imooc.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "订单管理接口", tags = {"订单管理的接口"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "获取需要评价的商品列表信息", notes = "获取需要评价的商品列表信息", httpMethod = "GET")
    @PostMapping("/pending")
    public IMOOCJSONResult pending(@RequestParam String userId, @RequestParam String orderId) {
        // 检查商品订单和用户id是否匹配
        boolean flag = orderService.checkOrderAndUserId(userId, orderId);
        if (!flag) {
            return IMOOCJSONResult.errorMsg("商品订单和用户id不匹配");
        }
        // 检查订单是否已经评价过
        Integer isComment = orderService.getOrder(orderId).getIsComment();
        if (isComment == YesOrNo.YES.getType()) {
            return IMOOCJSONResult.errorMsg("订单已经评价过了!");
        }
        // 获取订单包含的商品信息列表
        List<OrderItems> orderItemsList = myCommentsService.listOrderItems(orderId);
        return IMOOCJSONResult.ok(orderItemsList);

    }

    @ApiOperation(value = "保存用户提交的评价", notes = "保存用户提交的评价", httpMethod = "POST")
    @PostMapping("/saveList")
    public IMOOCJSONResult saveList(@RequestParam String userId, @RequestParam String orderId,@RequestBody List<OrderItemBO> list) {
        // 检查商品订单和用户id是否匹配
        boolean flag = orderService.checkOrderAndUserId(userId, orderId);
        if (!flag) {
            return IMOOCJSONResult.errorMsg("商品订单和用户id不匹配");
        }
        // 检查订单是否已经评价过
        Integer isComment = orderService.getOrder(orderId).getIsComment();
        if (isComment == YesOrNo.YES.getType()) {
            return IMOOCJSONResult.errorMsg("订单已经评价过了!");
        }
        // 检查传入的评价list是有效
        if (CollectionUtils.isEmpty(list)){
            return IMOOCJSONResult.errorMsg("传入的参数为空");
        }
        // 存储用户评价
        myCommentsService.saveComments(userId,orderId,list);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询用户评价记录的接口", notes = "查询用户评价记录的接口", httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
        @RequestParam String userId,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false)  Integer pageSize) {

        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("用户id为空");
        }
        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        PagedGridResult pagedGridResult = myCommentsService.pageMyComments(userId, page, pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }


}
