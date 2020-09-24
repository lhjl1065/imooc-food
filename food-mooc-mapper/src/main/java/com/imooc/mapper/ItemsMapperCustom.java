package com.imooc.mapper;

import com.imooc.pojo.vo.SearchItemVo;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemsMapperCustom {
    List<SearchItemVo> getSearchItemList(@Param("map") HashMap map);
}
