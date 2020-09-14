package com.imooc.controller;

import com.imooc.pojo.Stu;
import com.imooc.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/stu")
    public Object show(int id){
        return testService.queryById(id);
    }
    @PostMapping("/stu")
    public Object saveStu(@RequestBody Stu stu){
        testService.saveStu(stu);
        return "ok";
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
