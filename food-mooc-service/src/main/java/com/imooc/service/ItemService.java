package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import java.util.List;

public interface ItemService {

    Items getItems(String itemId);
    ItemsParam getItemParams(String itemId);
    List<ItemsImg> getItemImgList(String itemId);
    List<ItemsSpec> getItemSpecList(String itemId);
}
