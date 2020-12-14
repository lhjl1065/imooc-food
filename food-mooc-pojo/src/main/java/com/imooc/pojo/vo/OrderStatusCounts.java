package com.imooc.pojo.vo;

import lombok.Data;

/**
 * OrderStatusCounts 统计用户订单信息
 *
 * @author linHu daXia
 * @date 2020/12/13 20:35
 */
@Data
public class OrderStatusCounts {

    /**
     * 待付款订单数
     */
    private Integer waitPayCounts;
    /**
     * 待发货订单数
     */
    private Integer waitDeliverCounts;
    /**
     * 待收货订单数
     */
    private Integer waitReceiveCounts;
    /**
     * 待评价订单数
     */
    private Integer waitCommentCounts;
}
