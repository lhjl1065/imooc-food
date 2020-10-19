package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.bo.ShopCartBo;
import com.imooc.pojo.vo.ShopCartVo;
import com.imooc.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "购物车接口",tags = {"购物车相关接口"})
@RequestMapping("/shopcart")
public class ShopCartController {
    @Autowired
    ShopCartService shopCartService;

    @PostMapping("/add")
    @ApiOperation(value = "新增商品后同步购物车数据的接口",notes = "新增同步购物车数据的接口",httpMethod = "POST")
    public IMOOCJSONResult add(@RequestBody ShopCartBo shopCartBo,@RequestParam @ApiParam(value = "用户id",example = "123",required = true) String userId){
        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("用户id为空");
        }
        System.out.println(shopCartBo);
        return IMOOCJSONResult.ok();
        //TODO 后续存储购物车信息
    }
    @PostMapping("del")
    @ApiOperation(value = "删除商品后同步购物车数据的接口",notes = "删除商品后同步购物车数据的接口",httpMethod = "POST")
    public IMOOCJSONResult add(
        @RequestParam @ApiParam(value = "商品id",example = "cake-1004-spec-1",required = true) String itemSpecId,
        @RequestParam @ApiParam(value = "用户id",example = "123",required = true) String userId){
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)){
            return IMOOCJSONResult.errorMsg("用户id或商品规格id不能为空");
        }
        System.out.println("userId:"+userId+" itemSpecId:"+itemSpecId);
        return IMOOCJSONResult.ok();
        //TODO 后续更新redis中的购物车信息
    }
}
