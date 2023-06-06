package com.github.iphase2.inlog.config;

import com.github.iphase2.inlog.aop.LogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: AutoConfig
 * @Author iPhase2
 * @Date 2023/6/5 11:03
 * @description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AutoLogProperties.class})
@ConditionalOnProperty(prefix = AutoLogProperties.PROPERTIES_NAME, name = AutoLogProperties.ENABLE, havingValue = "true")
public class AutoConfig {


    @Bean
    public LogAspect autoLog(AutoLogProperties logProperties) {
        log.info("==== >>>>[autoLog bean]:init...");
        return new LogAspect(logProperties);
    }
}
