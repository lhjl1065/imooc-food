package com.imooc.config;

import com.imooc.common.enums.OrderStatusEnum;
import com.imooc.common.utils.DateUtil;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 定时任务
 */
@Component
public class OrderJob {
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    /**
     * 定时任务演示
     */
//    @Scheduled(cron = "0/3 * * * * ? ")
//    public void showTime(){
//        System.out.println(DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
//    }

    /**
     * 关闭超时的订单(超过12小时未支付就关闭)
     */
    @Transactional
    @Scheduled(cron = "0/3 * * * * ? ")
    public void closeTimeoutOrders(){
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        //查询还处于支付状态的订单
        List<OrderStatus> orderStatusList = orderStatusMapper.select(orderStatus);
        for (OrderStatus orderStatus1:orderStatusList){
            int daysBetween = DateUtil.daysBetween(orderStatus1.getCreatedTime(), new Date());
            //如果订单创建时间已经超过1天还处于支付状态,把订单关闭
            if (daysBetween>=1){
                doClose(orderStatus1);
            }
        }
    }

    /**
     * 关闭订单的方法
     * @param orderStatus
     */
    void doClose(OrderStatus orderStatus){
        OrderStatus updateOrderStatus = new OrderStatus();
        updateOrderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
        updateOrderStatus.setCloseTime(new Date());
        updateOrderStatus.setOrderId(orderStatus.getOrderId());
        orderStatusMapper.insert(updateOrderStatus);
    }
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void method2(){
        try {
            int a=2/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
