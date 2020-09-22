package com.imooc.service.impl;

import com.imooc.common.enums.LevelEnum;
import com.imooc.mapper.CategoryMapper;
import com.imooc.mapper.CategoryMapperCustom;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsWithRootCatIdVo;
import com.imooc.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    /**
     * 获取所有的一级分类
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAllRootLevelCat() {
        //查询type为1的分类信息
        Example example = new Example(Category.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", LevelEnum.Root.getType());
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    /**
     * 获取某一级分类下的二级分类和三级分类信息
     * @param rootCatId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoryVo> getSubCatList(int rootCatId){
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    /**
     * 获取某一级分类的相关页面新商品信息
     * @param rootId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<NewItemsWithRootCatIdVo> getSixNewItemsLazy(int rootId){
        return categoryMapperCustom.getSixNewItemsLazy(rootId);
    }
}
