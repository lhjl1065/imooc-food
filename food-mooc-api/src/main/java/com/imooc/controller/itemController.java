package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.CommentVo;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@Api(value = "商品相关接口",tags = "商品相关接口")
public class itemController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(value ="查询商品详情接口",notes = "查询商品详情接口",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public Object info(@ApiParam(value = "商品id",required = true) @PathVariable String itemId){
        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(itemService.getItems(itemId));
        itemInfoVo.setItemImgList(itemService.getItemImgList(itemId));
        itemInfoVo.setItemSpecList(itemService.getItemSpecList(itemId));
        itemInfoVo.setItemParams(itemService.getItemParams(itemId));
        return IMOOCJSONResult.ok(itemInfoVo);
    }
    @ApiOperation(value = "查询评价总数相关接口",notes = "查询评价总数相关接口",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public Object commentLevel(@RequestParam @ApiParam(value = "商品id",required = true) String itemId){
        CommentCountsVo commentCounts = itemService.getCommentCounts(itemId);
        return IMOOCJSONResult.ok(commentCounts);
    }
    @ApiOperation(value = "查询评价列表的接口",notes = "查询评价列表的接口",httpMethod = "GET")
    @GetMapping("/comments")
    public Object comments (
        @RequestParam @ApiParam(value = "商品id") String itemId,
        @RequestParam @ApiParam(value = "评价等级") Integer level,
        @RequestParam @ApiParam(value = "当前页") Integer page,
        @RequestParam @ApiParam(value = "每页显示数") Integer pageSize){
        PagedGridResult commentVoList = itemService.getCommentVoList(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(commentVoList);
    }
}
