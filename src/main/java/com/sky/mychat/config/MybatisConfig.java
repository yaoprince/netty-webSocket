package com.sky.mychat.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tiankong
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.sky.mychat.mapper")
public class MybatisConfig {
}
