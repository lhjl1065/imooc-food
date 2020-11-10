package com.imooc.service.transaction;

/**
 * 定义事务相关的方法
 * @author linghu
 * @date 2020/11/10 10:54
 */
public interface TransactionAction {

    /**
     * 事务回调方法
     */
    void callback();
}
