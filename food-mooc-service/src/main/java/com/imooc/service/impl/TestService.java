package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    private StuMapper stuMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    public Stu queryById(int id){
        return stuMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStu(Stu stu) {
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStuById(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStu(Stu stu) {
        saveChildren1();
        saveChildren2();
    }

    /*
    测试事务相关的几个Service方法
     */
    public void saveParent(){
        Stu parent = new Stu(1, "parent", 22);
        stuMapper.insert(parent);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveChildren(){
        try {
            saveChildren1();
            int i=1/0;
            saveChildren2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveChildren1(){
        stuMapper.insert(new Stu(2,"children1",1));
    }

    public void saveChildren2(){
        stuMapper.insert(new Stu(3,"children2",1));
    }
}
