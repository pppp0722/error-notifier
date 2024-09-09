package com.ilhwanlee.producer.notification.adapter.in.web.dto;

import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.common.web.exception.ErrorCode;
import com.ilhwanlee.producer.notification.application.in.command.PublishNotiEventCommand;
import com.ilhwanlee.producer.notification.domain.Severity;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 장애 알림 전송 Message 발행 Request DTO
 */
public record NotiEventPublishingRequestDto(
        @NotEmpty(message = "Target should not be empty and must have at least one element.")
        List<String> target,
        @NotEmpty(message = "Severity should not be empty.")
        String severity,
        @NotEmpty(message = "Message should not be empty.")
        String message
) {

    public PublishNotiEventCommand toCommand() {
        Severity severityEnum;

        try {
            severityEnum = Severity.valueOf(severity.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_SEVERITY);
        }

        return new PublishNotiEventCommand(target, severityEnum, message);
    }
}
