package com.sky.mychat;

import com.sky.mychat.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author tiankong
 */
@SpringBootApplication
@EnableCaching
public class MyChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyChatApplication.class, args);
    }

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

}
