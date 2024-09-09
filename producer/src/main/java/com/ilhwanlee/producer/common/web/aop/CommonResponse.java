package com.ilhwanlee.producer.common.web.aop;

import lombok.Builder;

/**
 * 공통 Response 포맷
 */
@Builder
public record CommonResponse<T>(
        int httpCode,
        String httpMessage,
        String moreInformation,
        T data
) {
}