package com.imooc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FileProperties 文件配置类
 *
 * @author linHu daXia
 * @date 2020/11/29 21:41
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "file-path")
public class FileProperties {

    /**
     * 图片本地磁盘存放位置
     */
    private String imgUserFaceLocation;
}
