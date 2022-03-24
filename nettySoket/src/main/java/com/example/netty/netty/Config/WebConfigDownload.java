package com.example.netty.netty.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigDownload implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // /home/file/**为前端URL访问路径 后面 file:xxxx为本地磁盘映射
        registry.addResourceHandler("/hbj/**").addResourceLocations("file:/opt/TongWeb/baosightFile/baosight/");
    }

}
