package com.imooc.service.impl;

import com.imooc.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    @Autowired
    TestService testService;
    @Transactional(propagation = Propagation.REQUIRED)
    public void testTransaction(){
        System.out.println("testTransaction执行了");
        testService.saveParent();
        try {
            testService.saveChildren();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i=1/0;
    }
}
