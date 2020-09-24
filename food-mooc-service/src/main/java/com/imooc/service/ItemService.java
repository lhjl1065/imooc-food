package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.CommentVo;
import java.util.List;

public interface ItemService {

    Items getItems(String itemId);
    ItemsParam getItemParams(String itemId);
    List<ItemsImg> getItemImgList(String itemId);
    List<ItemsSpec> getItemSpecList(String itemId);
    CommentCountsVo getCommentCounts(String itemId);
    PagedGridResult getCommentVoList(String itemId,Integer level,Integer page,Integer pageSize);
    PagedGridResult getSearchItemVoListByKeywords(String keywords,String sort,Integer page,Integer pageSize);
    PagedGridResult getSearchItemVoListByCatId(Integer catId,String sort,Integer page,Integer pageSize);
}
