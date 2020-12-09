package com.imooc.pojo.vo;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVo {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单创建时间
     */
    private Date createdTime;
    /**
     * 支付方式
     */
    private Integer payMethod;
    /**
     * 邮费
     */
    private Integer postAmount;

    /**
     * 实际支付总价格
     */
    private Integer realPayAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 子订单
     */
    private List<SubOrderVo> subOrderItemList;

}
