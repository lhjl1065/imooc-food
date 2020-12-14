package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdersMapperCustom extends MyMapper<Orders> {
    List<OrderVo> listMyOrders(@Param("userId") String userId,@Param("orderStatus") Integer orderStatus);
    int countStatus(@Param("userId") String userId,@Param("status") Integer status, @Param("isComment") Integer isComment);
    List<OrderStatus> pageOrderTrend(@Param("userId") String userId);
}
