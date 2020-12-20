package com.imooc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * url配置类
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "url-config")
public class UrlProperties {

    /**
     * 图片本地磁盘存放位置
     */
    private String fileUrlPathHeader;
    private String notifyUrl;
}
