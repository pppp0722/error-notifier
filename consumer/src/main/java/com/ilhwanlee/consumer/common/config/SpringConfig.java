package com.ilhwanlee.consumer.common.config;

import com.ilhwanlee.consumer.common.properties.UrlProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UrlProperties.class)
public class SpringConfig {
}
