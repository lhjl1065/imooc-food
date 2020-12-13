package com.imooc.pojo.bo;

import javax.persistence.Column;
import javax.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
public class OrderItemBO {
    /**
     * 主键id
     */
    private String id;

    /**
     * 归属订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品图片
     */
    private String itemImg;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 规格id
     */
    private String itemSpecId;

    /**
     * 规格名称
     */
    private String itemSpecName;

    /**
     * 成交价格
     */
    private Integer price;

    /**
     * 购买数量
     */
    private Integer buyCounts;

    /**
     * 评价id(用于入库)
     */
    private String commentId;

    /**
     * 评价等级
     */
    private Integer commentLevel;

    /**
     * 评价等级
     */
    private String content;


}
