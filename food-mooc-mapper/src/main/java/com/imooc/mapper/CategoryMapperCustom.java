package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsWithRootCatIdVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapperCustom {
    List<CategoryVo> getSubCatList(@Param("rootCatId") int rootCatId);
    List<NewItemsWithRootCatIdVo> getSixNewItemsLazy(@Param("rootCatId") int rootCatId);
}
