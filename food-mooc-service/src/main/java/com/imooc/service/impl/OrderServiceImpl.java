package com.imooc.service.impl;

import com.imooc.common.enums.OrderStatusEnum;
import com.imooc.common.enums.PayMethod;
import com.imooc.common.enums.YesOrNo;
import com.imooc.common.utils.CookieUtils;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.common.utils.JsonUtils;
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
import com.imooc.pojo.bo.MerchantOrdersBO;
import com.imooc.pojo.bo.OrderBo;
import com.imooc.service.OrderService;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Slf4j
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
    private RestTemplate restTemplate;

    @Autowired
    private Sid sid;



    @Transactional(propagation = Propagation.REQUIRED)
    public String create(OrderBo orderBo) {
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
            //对1应规格的库存要减去购买数
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

        //返回订单id
        return orderId;
    }

    public IMOOCJSONResult sendOrderToPayCenter(String orderId, HttpServletRequest request) {
        //封装发送的bo类
        MerchantOrdersBO merchantOrdersBO = new MerchantOrdersBO();
        merchantOrdersBO.setMerchantOrderId(orderId);
        merchantOrdersBO.setPayMethod(PayMethod.WEIXIN.type);
        String userStr = CookieUtils.getCookieValue(request, "user",true);
        Users user = JsonUtils.jsonToPojo(userStr, Users.class);
        merchantOrdersBO.setMerchantUserId(user.getId());
//        merchantOrdersBO.setAmount(ordersMapper.selectByPrimaryKey(orderId).getRealPayAmount());真实价格
        //方便测试所有价格改为一分钱
        merchantOrdersBO.setAmount(1);
        merchantOrdersBO.setReturnUrl("http://kgb4db.natappfree.cc/orders/notifyMerchantOrderPaid");
        //发送http请求给聚合支付中心
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId","imooc");
        httpHeaders.add("password","imooc");
        String url="http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
        HttpEntity<MerchantOrdersBO> httpEntity = new HttpEntity<>(merchantOrdersBO, httpHeaders);
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate
            .postForEntity(url, httpEntity, IMOOCJSONResult.class);
        IMOOCJSONResult result = responseEntity.getBody();
        Integer statusCode = result.getStatus();
        if (statusCode!=200){
            log.error("向聚合支付中心提交订单失败");
            return IMOOCJSONResult.errorMsg("订单提交失败");
        }
        return IMOOCJSONResult.ok(orderId);


    }

    @Transactional
    public Integer updateOrderStatus(OrderStatusEnum orderStatusEnum, String orderId) {
        OrderStatus updateOrderStatus = new OrderStatus();
        switch(orderStatusEnum.value){
            case "待发货":
                updateOrderStatus.setPayTime(new Date());
                break;
            case "待收货":
                updateOrderStatus.setDeliverTime(new Date());
                break;
            case "交易成功":
                updateOrderStatus.setSuccessTime(new Date());
                break;
            case "交易失败":
                updateOrderStatus.setCloseTime(new Date());
                break;
        }
        updateOrderStatus.setOrderId(orderId);
        updateOrderStatus.setOrderStatus(orderStatusEnum.type);
        int result = orderStatusMapper.updateByPrimaryKeySelective(updateOrderStatus);
        if (result!=1){
            log.error("更新订单状态失败");
            return 500;
        }
        log.info("订单{}更新为{}状态成功",orderId,orderStatusEnum.value);
        return 200;
    }

    @Override
    public OrderStatus queryOrderStatus(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public boolean checkOrderAndUserId(String userId, String orderId) {
        Orders queryOrder = new Orders();
        queryOrder.setId(orderId);
        queryOrder.setUserId(userId);
        List<Orders> list = ordersMapper.select(queryOrder);
        if (list.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deliver(String orderId) {
        OrderStatus updateOrderStatus = new OrderStatus();
        updateOrderStatus.setDeliverTime(new Date());
        updateOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        Example example = new Example(OrderStatus.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId)
            .andEqualTo("orderStatus",OrderStatusEnum.WAIT_DELIVER.type);
        int count = orderStatusMapper.updateByExampleSelective(updateOrderStatus, example);
        return count > 0;
    }

    @Override
    public boolean confirmReceive(String orderId) {
        OrderStatus updateOrderStatus = new OrderStatus();
        updateOrderStatus.setSuccessTime(new Date());
        updateOrderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        Example example = new Example(OrderStatus.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId)
            .andEqualTo("orderStatus",OrderStatusEnum.WAIT_RECEIVE.type);
        int i = orderStatusMapper.updateByExampleSelective(updateOrderStatus, example);
        return i > 0;
    }
}
