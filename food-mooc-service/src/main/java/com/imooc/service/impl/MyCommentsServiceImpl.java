package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.common.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.bo.OrderItemBO;
import com.imooc.pojo.vo.MyCommentVO;
import com.imooc.service.MyCommentsService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private Sid sid;


    /**
     * 获取订单的商品列表(快照)
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItems> listOrderItems(String orderId) {
        Example example = new Example(OrderItems.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        return orderItemsMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String userId, String orderId,List<OrderItemBO> list) {
        // 存储用户评价
        for (OrderItemBO orderItemBO:list){
            orderItemBO.setCommentId(sid.nextShort());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("commentList",list);
        map.put("userId",userId);
        Integer integer = itemsCommentsMapperCustom.saveItemCommentList(map);
        System.out.println(integer);
        // 更改订单isComment字段
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUpdatedTime(new Date());
        orders.setIsComment(YesOrNo.YES.getType());
        ordersMapper.updateByPrimaryKeySelective(orders);
        // 更改订单状态表的评价时间字段
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public PagedGridResult pageMyComments(String userId, Integer page, Integer pageSize) {
        //开始分页
        PageHelper.startPage(page,pageSize);
        List<MyCommentVO> myCommentVOS = itemsCommentsMapperCustom.pageMyComments(userId);
        return setterPagedGridResult(myCommentVOS,page);
    }

    PagedGridResult setterPagedGridResult(List<?> list,Integer page){
        PageInfo pageList = new PageInfo(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return pagedGridResult;
    }
}
