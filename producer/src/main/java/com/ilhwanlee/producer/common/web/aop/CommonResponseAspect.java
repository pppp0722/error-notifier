package com.ilhwanlee.producer.common.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
class CommonResponseAspect {

    @Around("execution(* com.ilhwanlee.producer..*Controller.*(..))")
    public ResponseEntity<CommonResponse<Object>> wrapInApiResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        int httpCode = HttpStatus.OK.value();
        String httpMessage = HttpStatus.OK.getReasonPhrase();
        HttpHeaders headers = new HttpHeaders();
        Object data = result;

        if (result instanceof ResponseEntity<?> entity) {
            HttpStatus httpStatus = (HttpStatus) entity.getStatusCode();
            httpCode = httpStatus.value();
            httpMessage = httpStatus.getReasonPhrase();
            headers = entity.getHeaders();
            data = entity.getBody();
        }

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .httpCode(httpCode)
                .httpMessage(httpMessage)
                .data(data)
                .build();

        return ResponseEntity.status(httpCode).headers(headers).body(commonResponse);
    }
}
