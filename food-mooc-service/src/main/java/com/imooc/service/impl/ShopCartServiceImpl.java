package com.imooc.service.impl;

import com.imooc.common.utils.IMOOCJSONResult;
import com.imooc.mapper.ItemsMapperCustom;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.ShopCartVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopCartServiceImpl implements com.imooc.service.ShopCartService {
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopCartVo> findShopCartItems(List itemSpecIdList) {
        return itemsMapperCustom.getShopCartVoList(itemSpecIdList);
    }
}
