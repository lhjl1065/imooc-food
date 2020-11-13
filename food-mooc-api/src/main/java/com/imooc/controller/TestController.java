package com.imooc.controller;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.impl.TestService;
import org.n3r.idworker.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RestController
public class TestController {
    private static final Logger logger=LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TestService testService;
    @Autowired
    private StuMapper stuMapper;

    @GetMapping("/stu")
    public Object show(int id){
        logger.info("进入/stu方法是执行");
        return testService.queryById(id);
    }
    @PostMapping("/stu")
    public Object saveStu(@RequestBody Stu stu){
        testService.saveStu(stu);
        return "ok "+stu.toString();
    }
    @DeleteMapping("/stu")
    public Object deleteStuById(int id){
        testService.deleteStuById(id);
        return "ok";
    }
    @PutMapping("/stu")
    public Object updateStu(@RequestBody Stu stu){
        testService.updateStu(stu);
        return "ok";
    }
}
