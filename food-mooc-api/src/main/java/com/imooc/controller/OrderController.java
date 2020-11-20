package com.imooc.controller;

import com.imooc.common.enums.OrderStatusEnum;
import com.imooc.common.enums.PayMethod;
import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.MerchantOrdersBO;
import com.imooc.pojo.bo.NoteBo;
import com.imooc.pojo.bo.OrderBo;
import com.imooc.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        //订单入库(天天吃货中心)
        String orderId = orderService.create(orderBo);
        //todo 更新redis中的购物车

        //同步前端中的购物车数据(同步cookie)
//        CookieUtils.setCookie(request,response,"shopcart","",true);
        //给聚合支付中心发送交易请求
        return orderService.sendOrderToPayCenter(orderId,request);


    }
    @ApiOperation(value = "接收聚合支付中心通知的接口",notes = "接收聚合支付中心通知的接口",httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){
        //根据这个订单号修改订单状态为支付完成
        return orderService.updateOrderStatus(OrderStatusEnum.WAIT_DELIVER,merchantOrderId);
    }

    @ApiOperation(value = "用于前端查询订单支付状态的接口",notes = "用于前端查询订单支付状态的接口",httpMethod = "POST")
    @PostMapping("/getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId){
        //查询订单状态
        OrderStatus orderStatus = orderService.queryOrderStatus(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }


}
