package com.imooc;

import com.imooc.service.impl.TransactionService;
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
        transactionService.testTransaction();
    }
}
