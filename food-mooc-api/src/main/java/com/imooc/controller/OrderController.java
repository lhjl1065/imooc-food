package com.imooc.controller;

import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.bo.OrderBo;
import com.imooc.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Api(tags = "订单相关的接口")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "创建订单的接口",notes = "创建订单的接口",httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(
        @RequestBody OrderBo orderBo,
        HttpServletRequest request,
        HttpServletResponse response) {
        //订单入库
        String orderId = orderService.create(orderBo);
        //todo 更新redis中的购物车

        //同步前端中的购物车数据(同步cookie)
//        CookieUtils.setCookie(request,response,"shopcart","",true);
        //shift+f5强制刷新页面
        return IMOOCJSONResult.ok(orderId);

    }

}
