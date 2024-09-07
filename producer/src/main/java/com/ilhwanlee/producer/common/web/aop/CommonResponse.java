package com.ilhwanlee.producer.common.web.aop;

import lombok.Builder;

@Builder
public record CommonResponse<T>(
        int httpCode,
        String httpMessage,
        String moreInformation,
        T data
) {
}