package com.imooc.exception;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * TestDemo TODO
 *
 * @author linHu daXia
 * @date 2020/12/12 0:11
 */
@Slf4j
public class TestDemo {

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("statusList", new String[]{"1","2","3"});
        HashMap<String, Object> map1 = new HashMap<>();
        map1.putAll(map);
        log.info("{}",map1);
    }
}
