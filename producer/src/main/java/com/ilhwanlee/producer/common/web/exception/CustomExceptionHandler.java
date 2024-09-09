package com.ilhwanlee.producer.common.web.exception;

import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.producer.common.web.aop.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * CustomException, ValidationException 등을 캐치해 Error Response하기 위한 ExceptionHandler Class
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<CommonResponse<Object>> handleCustomException(CustomException e) {
        return handleCustomExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<CommonResponse<Object>> handleValidationException(MethodArgumentNotValidException e) {
        return handleCustomExceptionInternal(ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<CommonResponse<Object>> handleInvalidRequestException(HttpMessageNotReadableException e) {
        return handleCustomExceptionInternal(ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<CommonResponse<Object>> handlerUnexpectedException(Exception e) {
        LoggingUtils.logError("Unexpected error occurred.", e);
        return handleCustomExceptionInternal(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<CommonResponse<Object>> handleCustomExceptionInternal(ErrorCode errorCode) {
        CommonResponse<Object> response = CommonResponse.builder()
                .httpCode(errorCode.getHttpCode())
                .httpMessage(errorCode.getHttpMessage())
                .moreInformation(errorCode.getMoreInformation())
                .build();
        return ResponseEntity.status(errorCode.getHttpCode()).body(response);
    }
}
