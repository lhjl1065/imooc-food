package com.imooc.controller;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.CommentVo;
import com.imooc.pojo.vo.ItemInfoVo;
import com.imooc.pojo.vo.ShopCartVo;
import com.imooc.service.ItemService;
import com.imooc.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private ShopCartService shopCartService;

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
        @RequestParam @ApiParam(value = "商品id",required = true) String itemId,
        @RequestParam @ApiParam(value = "评价等级",required = true) Integer level,
        @RequestParam @ApiParam(value = "当前页") Integer page,
        @RequestParam @ApiParam(value = "每页显示数") Integer pageSize){
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=10;
        }
        PagedGridResult commentVoList = itemService.getCommentVoList(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(commentVoList);
    }

    @ApiOperation(value = "关键词搜索商品的接口",notes = "关键词搜索商品的接口",httpMethod = "GET")
    @GetMapping("/search")
    public Object comments (
        @RequestParam @ApiParam(value = "关键词",required = true) String keywords,
        @RequestParam @ApiParam(value = "排序方式") String sort,
        @RequestParam @ApiParam(value = "当前页") Integer page,
        @RequestParam @ApiParam(value = "每页显示数") Integer pageSize){
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=10;
        }
        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg("无关键字");
        }
        PagedGridResult searchItemList = itemService.getSearchItemVoListByKeywords(keywords,sort, page, pageSize);
        return IMOOCJSONResult.ok(searchItemList);
    }

    @ApiOperation(value = "分类Id搜索商品的接口",notes = "分类ID搜索商品的接口",httpMethod = "GET")
    @GetMapping("/catItems")
    public Object catItems (
        @RequestParam @ApiParam(value = "分类ID",required = true) Integer catId,
        @RequestParam @ApiParam(value = "排序方式") String sort,
        @RequestParam @ApiParam(value = "当前页") Integer page,
        @RequestParam @ApiParam(value = "每页显示数") Integer pageSize){
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=10;
        }
        if ((catId==null)){
            return IMOOCJSONResult.errorMsg("无分类id");
        }
        PagedGridResult searchItemList = itemService.getSearchItemVoListByCatId(catId,sort, page, pageSize);
        return IMOOCJSONResult.ok(searchItemList);
    }
    @GetMapping("/refresh")
    @ApiOperation(value = "刷新商品信息接口",notes = "刷新商品信息接口",httpMethod = "GET")
    public IMOOCJSONResult refresh(
        @ApiParam(value = "商品分类id字符串",example = "cake-1004-spec-1,cake-1004-spec-2",required = true)  @RequestParam String itemSpecIds){
        if (StringUtils.isBlank(itemSpecIds)){
            return IMOOCJSONResult.errorMsg("商品规格数组字符串不能为空");
        }
        String[] itemSpecIdsArray = itemSpecIds.split(",");
        List<String> itemSpecIdList = (List<String>) Arrays.asList(itemSpecIdsArray);
        if (itemSpecIdList.size()<1){
            return IMOOCJSONResult.errorMsg("规格id数组不能小于1");
        }
        List<ShopCartVo> shopCartItems = shopCartService.findShopCartItems(itemSpecIdList);
        return IMOOCJSONResult.ok(shopCartItems);
    }

}
