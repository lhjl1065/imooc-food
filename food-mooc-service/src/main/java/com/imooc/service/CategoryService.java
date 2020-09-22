package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsWithRootCatIdVo;
import java.util.List;

public interface CategoryService {
    List<Category>queryAllRootLevelCat();
    List<CategoryVo> getSubCatList(int rootCatId);
    List<NewItemsWithRootCatIdVo> getSixNewItemsLazy(int rootCatId);

}
