package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        //添加允许跨域的访问源
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://qupcag.natappfree.cc");
        config.addAllowedOrigin("http://localhost:8081");
        //添加允许跨域的Http方法
        config.addAllowedMethod("*");
        //添加允许的头信息
        config.addAllowedHeader("*");
        //设置允许跨域请求带上凭证(cookie)
        config.setAllowCredentials(true);
        //为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);
        //返回跨域请求过滤器
        return new CorsFilter(corsSource);
    }

}
