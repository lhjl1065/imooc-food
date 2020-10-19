package com.imooc.service;

import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.ShopCartVo;
import java.util.List;

public interface ShopCartService {

    List<ShopCartVo> findShopCartItems(List itemSpecIdList);
}
