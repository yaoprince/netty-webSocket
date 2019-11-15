package com.sky.mychat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 自定义的netty.xml需要配置到框架,否则找不到netty.xml文件注入的类
 *
 * @author tiankong
 */
@Configuration
@ImportResource(locations = {"classpath:netty.xml"})
public class NettyConfig {
}
