package com.lanyage.multidatasource.autoconfig;

import com.lanyage.multidatasource.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class UserAutoConfig {
    private static final Logger logger = LoggerFactory.getLogger(UserAutoConfig.class);
    @Bean("testUser")
    public User user() {
        logger.info(">>>>>> Auto Confiuration of Test USER >>>>");
        return new User();
    }
}
