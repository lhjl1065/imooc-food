package com.imooc.service.impl;

import com.imooc.common.enums.OrderStatusEnum;
import com.imooc.common.enums.YesOrNo;
import com.imooc.manager.ItemManager;
import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.OrderBo;
import com.imooc.service.OrderService;
import java.util.Date;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ItemManager itemManager;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    public void create(OrderBo orderBo) {
        //封装订单表数据
        Orders orders = new Orders();
        Users user = usersMapper.selectByPrimaryKey(orderBo.getUserId());
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(orderBo.getAddressId());
        String orderId = sid.nextShort();
        orders.setId(orderId);
        orders.setUserId(orderBo.getUserId());
        orders.setReceiverName(user.getUsername());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(
            userAddress.getProvince()+" "+
                userAddress.getCity()+" "+
                userAddress.getDetail());
        Integer totalAmount = 0;
        Integer realPayAmount = 0;
        //邮费设为0
        Integer postAmount = 0;
        orders.setPostAmount(postAmount);
        orders.setPayMethod(orderBo.getPayMethod());
        orders.setLeftMsg(orderBo.getLeftMsg());
        orders.setIsComment(YesOrNo.NO.getType());
        orders.setIsDelete(YesOrNo.NO.getType());
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        //封装订单关联表数据
        String itemSpecIdsStr = orderBo.getItemSpecIds();
        String[] itemSpecIds = itemSpecIdsStr.split(",");
        OrderItems orderItems = new OrderItems();
        for (String itemSpecId:itemSpecIds){
            ItemsSpec itemsSpec = itemsSpecMapper.selectByPrimaryKey(itemSpecId);
            orderItems.setId(sid.nextShort());
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemsSpec.getItemId());
            Items items = itemsMapper.selectByPrimaryKey(itemsSpec.getItemId());
            ItemsImg itemsImg = itemManager.getItemsMainImg(itemsSpec.getItemId());
            orderItems.setItemImg(itemsImg.getUrl());
            orderItems.setItemName(items.getItemName());
            orderItems.setItemSpecId(itemsSpec.getId());
            orderItems.setItemSpecName(itemsSpec.getName());
            Integer priceNormal = itemsSpec.getPriceNormal();
            Integer priceDiscount = itemsSpec.getPriceDiscount();
            totalAmount+=priceNormal * 1;
            realPayAmount+=priceDiscount * 1;
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            //todo 因为购物数量要从购物车里面取,所以先默认为1件
            orderItems.setBuyCounts(1);
            orderItemsMapper.insert(orderItems);
            //对应规格的库存要减去购买数
            itemManager.decreaseStock(itemSpecId,1);

        }
        orders.setRealPayAmount(realPayAmount);
        orders.setTotalAmount(totalAmount);
        ordersMapper.insert(orders);
        //存储订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreatedTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatusMapper.insert(orderStatus);
        //更新该用户的购物车数据

        //向支付中心发起请求
    }
}
