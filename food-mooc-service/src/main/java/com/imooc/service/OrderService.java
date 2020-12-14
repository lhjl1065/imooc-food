package com.imooc.service;

import com.imooc.common.enums.OrderStatusEnum;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.bo.MerchantOrdersBO;
import com.imooc.pojo.bo.OrderBo;
import com.imooc.pojo.vo.OrderStatusCounts;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public interface OrderService {

    /**
     * 提交订单
     * @param orderBo
     */
    String create(OrderBo orderBo);

    IMOOCJSONResult sendOrderToPayCenter(String orderId, HttpServletRequest request);

    /**
     * 更新订单的状态
     *
     * @param orderStatusEnum
     * @param orderId
     * @return
     */
    Integer updateOrderStatus(OrderStatusEnum orderStatusEnum,String orderId);
    OrderStatus queryOrderStatus(String orderId);

    /**
     * 验证该用户是否存在该订单
     * @param userId
     * @param orderId
     * @return
     */
    boolean checkOrderAndUserId(String userId, String orderId);

    boolean deliver(String orderId);

    boolean confirmReceive(String orderId);

    Orders getOrder(String orderId);

    void delete(String orderId);

    /**
     * 统计用户的订单信息
     * @param userId
     * @return
     */
    OrderStatusCounts statusCounts(String userId);

    /**
     * 分页查询订单动向信息
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult pageOrderTrend(String userId, Integer page, Integer pageSize);
}
