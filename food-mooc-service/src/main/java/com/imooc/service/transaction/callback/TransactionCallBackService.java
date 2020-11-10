package com.imooc.service.transaction.callback;


import com.imooc.service.transaction.TransactionAction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author linghu
 * @date 2020/11/10 10:53
 */
@Component
public class TransactionCallBackService {

    public void execute(final TransactionAction action) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            //添加事务注解时返回true
            TransactionSynchronizationManager.
                registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        // 事务提交后执行回调
                        action.callback();
                    }
                });
        } else {
            action.callback();
        }
    }
}
