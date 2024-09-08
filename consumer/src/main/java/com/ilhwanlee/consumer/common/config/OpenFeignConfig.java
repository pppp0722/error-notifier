package com.ilhwanlee.consumer.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.ilhwanlee.consumer.notification.adapter.out.web")
public class OpenFeignConfig {
}
