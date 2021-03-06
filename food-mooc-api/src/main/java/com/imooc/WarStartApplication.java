package com.imooc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * war包启动类
 */
public class WarStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //指向Application2这个springboot启动类
        return builder.sources(Application.class);
    }
}
