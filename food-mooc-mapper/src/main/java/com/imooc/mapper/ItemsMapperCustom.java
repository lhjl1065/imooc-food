package com.imooc.mapper;

import com.imooc.pojo.vo.SearchItemVo;
import com.imooc.pojo.vo.ShopCartVo;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemsMapperCustom {
    List<SearchItemVo> getSearchItemList(@Param("map") HashMap map);
    List<ShopCartVo> getShopCartVoList(@Param("itemSpecIdList") List itemSpecIds);
    Integer decreaseStock(@Param(("itemSpecId")) String itemSpecId,@Param("buyCounts") Integer buyCounts);
}
