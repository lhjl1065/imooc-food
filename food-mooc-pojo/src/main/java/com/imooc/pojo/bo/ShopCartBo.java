package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "购物车商品对象",description = "用于接收前端传来的购物车商品对象")
public class ShopCartBo {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id",example = "cake-1001",required = true)
    private String itemId;
    /**
     * 商品图片url
     */
    @ApiModelProperty(value = "商品图片url",example = "http://122.152.205.72:88/foodie/cake-1001/img1.png",required = true)
    private String itemImgUrl;
    /**
     * 商品名字
     */
    @ApiModelProperty(value = "商品名字",example = "【天天吃货】真香预警 超级好吃 手撕面包 儿童早餐早饭",required = true)
    private String itemName;
    /**
     * 商品规格id
     */
    @ApiModelProperty(value = "商品规格id",example = "cake-1001-spec-1",required = true)
    private String specId;
    /**
     * 商品规格名称
     */
    @ApiModelProperty(value = "商品规格名称",example = "原味",required = true)
    private String specName;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量",example = "3",required = true)
    private int buyCounts;
    /**
     * 商品优惠价
     */
    @ApiModelProperty(value = "商品优惠价",example = "18000",required = true)
    private int priceDiscount;
    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价",example = "20000",required = true)
    private int priceNormal;
}
