package com.ilhwanlee.producer.notification.adapter.in.web;

import com.ilhwanlee.producer.notification.adapter.in.web.dto.NotiEventPublishingRequestDto;
import com.ilhwanlee.producer.notification.adapter.in.web.dto.NotiEventPublishingResponseDto;
import com.ilhwanlee.producer.notification.application.in.PublishNotiEventUseCase;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 에러 알림 전송 대상을 식별하여 Slack API 호출에 필요한 정보를 조회하여
 * Message Queue에 발행하기 위한 Controller
 */
@RestController
@RequestMapping("/v1/alerts")
@RequiredArgsConstructor
@Tag(name = "Noti event publishing API", description = "알림 이벤트 발행 API")
class NotiEventPublishingController {

    private final PublishNotiEventUseCase useCase;

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Ok")
    public ResponseEntity<NotiEventPublishingResponseDto> sendNoti(
            @Valid @RequestBody NotiEventPublishingRequestDto requestDto) {

        int targetUserCount = useCase.publishNotiEvent(requestDto.toCommand());
        NotiEventPublishingResponseDto responseDto = new NotiEventPublishingResponseDto(targetUserCount);

        return ResponseEntity.ok(responseDto);
    }
}
