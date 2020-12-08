package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderVo;
import java.util.List;

public interface OrdersMapperCustom extends MyMapper<Orders> {
    List<OrderVo> getMyOrders(String userId,Integer orderStatus);
}
