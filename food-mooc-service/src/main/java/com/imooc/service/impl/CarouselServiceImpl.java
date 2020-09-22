package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    public CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(int isShow) {
        //创建查询模板
        Example example = new Example(Carousel.class);
        //定义查询顺序根据sort字段并且为升序
        example.orderBy("sort").desc();
        Criteria criteria = example.createCriteria();
        //isShow字段要求值等于isShow变量的值
        criteria.andEqualTo("isShow",isShow);
        List<Carousel> carousels = carouselMapper.selectByExample(example);
        return carousels;
    }
}
