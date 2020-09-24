package com.imooc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.common.enums.CommentLevelenum;
import com.imooc.common.utils.DesensitizationUtil;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsMapperCustom;
import com.imooc.mapper.ItemsParamMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.vo.CommentCountsVo;
import com.imooc.pojo.vo.CommentVo;
import com.imooc.pojo.vo.SearchItemVo;
import com.imooc.service.ItemService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items getItems(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam getItemParams(String itemId) {
        Example example = new Example(ItemsParam.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> getItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> getItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CommentCountsVo getCommentCounts(String itemId) {
        CommentCountsVo commentCountsVo = new CommentCountsVo();
        Integer goodCounts = getCommentCountsByLevel(itemId, CommentLevelenum.good.getType());
        Integer normalCounts = getCommentCountsByLevel(itemId, CommentLevelenum.normal.getType());
        Integer badCounts = getCommentCountsByLevel(itemId, CommentLevelenum.bad.getType());
        Integer totalCounts=goodCounts+normalCounts+badCounts;
        commentCountsVo.setGoodCounts(goodCounts);
        commentCountsVo.setNormalCounts(normalCounts);
        commentCountsVo.setBadCounts(badCounts);
        commentCountsVo.setTotalCounts(totalCounts);
        return commentCountsVo;

    }
    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCountsByLevel(String itemId, Integer level){
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        itemsComments.setCommentLevel(level);
        return itemsCommentsMapper.selectCount(itemsComments);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult getCommentVoList(String itemId,Integer level,Integer page,Integer pageSize){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemId",itemId);
        map.put("commentLevel",level);
        PageHelper.startPage(page,pageSize);
        List<CommentVo> list = itemsCommentsMapperCustom.getItemCommentVoList(map);
        for (CommentVo commentVo:list){
            commentVo.setNickName(DesensitizationUtil.commonDisplay(commentVo.getNickName()));
        }
        return setterPagedGridResult(list,page);

    }
    @Transactional(propagation =Propagation.SUPPORTS)
    PagedGridResult setterPagedGridResult(List<?> list,Integer page){
        PageInfo pageList = new PageInfo(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return pagedGridResult;

    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult getSearchItemVoListByKeywords(String keyword,String sort,Integer page,Integer pageSize){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("keyword",keyword);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemVo> list = itemsMapperCustom.getSearchItemList(map);
        return setterPagedGridResult(list,page);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult getSearchItemVoListByCatId(Integer CatId,String sort,Integer page,Integer pageSize){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("catId",CatId);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemVo> list = itemsMapperCustom.getSearchItemList(map);
        return setterPagedGridResult(list,page);
    }
}
