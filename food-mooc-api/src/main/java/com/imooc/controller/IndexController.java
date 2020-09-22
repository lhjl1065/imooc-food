package com.imooc.controller;

import com.imooc.common.enums.YesOrNo;
import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsWithRootCatIdVo;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.im.InputMethodManager;

@RequestMapping("/index")
@RestController
@Api(value = "首页",tags = {"首页相关的接口"})
public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/carousel")
    @ApiOperation(value ="查询轮播图",notes = "查询轮图的接口" ,httpMethod = "GET")
    public Object queryAll(){
        //查询轮播图返回给前端
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.getType());
        return IMOOCJSONResult.ok(carousels);
    }
    @ApiOperation(value = "查询一级分类接口",notes = "查询一级分类接口",httpMethod = "GET")
    @GetMapping("/cats")
    public Object cats(){
        List<Category> categories = categoryService.queryAllRootLevelCat();
        return IMOOCJSONResult.ok(categories);
    }
    @ApiOperation(value = "查询二级和三级分类接口",notes = "查询二级和三级分类接口",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public Object subCat(@ApiParam(name = "rootCatId",value = "一级分类的id",required = true) @PathVariable Integer rootCatId){
        List<CategoryVo> subCatList = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(subCatList);
    }
    @ApiOperation(value = "获取某大类推荐商品的接口",notes = "获取某大类推荐商品的接口",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public Object sixNewItems(@ApiParam(name = "rootCatId",value = "一级分类的id",required = true) @PathVariable int rootCatId){
        List<NewItemsWithRootCatIdVo> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(sixNewItemsLazy);
    }
}
