package com.imooc.pojo.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "订单bo类")
public class OrderBo {
    @ApiModelProperty(value = "用户id",required = true,example ="200914HRBDYGH46W" )
    private String userId;

    @ApiModelProperty(value = "订单id字符串",required = true,example = "snacks-1006-spec-1,snacks-1006-spec-2")
    private String itemSpecIds;

    @ApiModelProperty(value = "地址id",required = true,example = "201020FADGRKZ2FW")
    private String addressId;

    @ApiModelProperty(value = "支付方式",required = true,example = "1")
    private Integer payMethod;

    @ApiModelProperty(value = "留言",required = false,example = "麻烦给我包装严实点!")
    private String leftMsg;

}
