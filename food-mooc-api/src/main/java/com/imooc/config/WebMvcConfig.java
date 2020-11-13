package com.imooc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * WebMvcConfig TODO
 *
 * @author linHu daXia
 * @date 2020/11/11 18:34
 */
@Configuration
public class WebMvcConfig {
    @Bean
    public RestTemplate getTestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
