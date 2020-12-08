package com.imooc.pojo.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubOrderVo {
    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品id
     */
    private String itemId;
    /**
     * 商品图片
     */
    private String itemImg;
    /**
     * 规格名称
     */
    private String itemSpecName;

    /**
     * 购买数量
     */
    private Integer buyCounts;

    /**
     * 成交价格
     */
    private Integer price;

}
