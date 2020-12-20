package com.imooc.config;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig TODO
 *
 * @author linHu daXia
 * @date 2020/11/11 18:34
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String faceLocation = fileProperties.getImgUserFaceLocation();
        registry.addResourceHandler("/**")
            .addResourceLocations("file:"+faceLocation+ "/")
            .addResourceLocations("classpath:/META-INF/resources/");
        HashMap<StringBuilder, String> stringBuilderStringHashMap = new HashMap<>();
    }

    @Bean
    public RestTemplate getTestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
