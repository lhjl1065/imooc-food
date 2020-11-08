package com.imooc.manager;

import com.imooc.common.enums.YesOrNo;
import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapperCustom;
import com.imooc.pojo.ItemsImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ItemManager {
    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    /**
     * 根据商品id获取商品的主图
     * @param itemsId
     * @return
     */
    public ItemsImg getItemsMainImg(String itemsId){
        ItemsImg queryItemsImg = new ItemsImg();
        queryItemsImg.setIsMain(YesOrNo.YES.getType());
        queryItemsImg.setItemId(itemsId);
        return itemsImgMapper.selectOne(queryItemsImg);
    }

    /**
     * 根据购买数减少相关规格商品的库存
     *
     * @param itemSpecId 规格id
     * @param buyCount   购买数量
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseStock(String itemSpecId, Integer buyCount){
        System.out.println("11");
        //使用乐观锁,如果库存小于购买数,则不保存,返回result 0,抛出异常,事务回滚.
        Integer result = itemsMapperCustom.decreaseStock(itemSpecId, buyCount);
        if (result!=1){
            throw new RuntimeException("库存不足,订单创建失败");
        }
    }
}
