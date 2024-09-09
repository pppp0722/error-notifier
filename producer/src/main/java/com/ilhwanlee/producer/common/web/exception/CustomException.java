package com.ilhwanlee.producer.common.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CustomExceptionHandler에서 ErrorCode만으로 Response를 구성할 수 있도록 custom한 Exception
 */
@RequiredArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
}
