package com.imooc;

import com.imooc.service.impl.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestTransaction {
    @Autowired
    TransactionService transactionService;

    @Test
    public void testTransaction(){
        int a=10;
        try {
            Assert.assertEquals(a,100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("11111111111");
//        transactionService.testTransaction();
    }
}
