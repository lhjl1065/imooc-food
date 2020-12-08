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
@ConfigurationProperties(prefix = "bone")
public class FileProperties {

    /**
     * 图片本地磁盘存放位置
     */
    private String imgUserFaceLocation;
    /**
     * 文件映射访问路径头
     */
    private String fileUrlPathHeader;

    public String getImgUserFaceLocation() {
        return imgUserFaceLocation;
    }

    public void setImgUserFaceLocation(String imgUserFaceLocation) {
        this.imgUserFaceLocation = imgUserFaceLocation;
    }
}
