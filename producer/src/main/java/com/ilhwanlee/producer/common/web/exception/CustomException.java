package com.ilhwanlee.producer.common.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
}
