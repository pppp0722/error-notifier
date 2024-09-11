package com.ilhwanlee.consumer.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
public record UrlProperties(String slack) {

}
