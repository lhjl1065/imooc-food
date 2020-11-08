package com.imooc.service;

import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.bo.OrderBo;
import org.springframework.beans.factory.annotation.Autowired;

public interface OrderService {

    /**
     * 提交订单
     * @param orderBo
     */
    void create(OrderBo orderBo);

}
