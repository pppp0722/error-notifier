package com.ilhwanlee.producer.notification.adapter.in;

import com.ilhwanlee.producer.notification.adapter.in.dto.NotiSendingRequestDto;
import com.ilhwanlee.producer.notification.adapter.in.dto.NotiSendingResponseDto;
import com.ilhwanlee.producer.notification.application.in.SendNotiUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alerts")
@RequiredArgsConstructor
public class NotiSendingController {

    private final SendNotiUseCase useCase;

    @PostMapping
    public ResponseEntity<NotiSendingResponseDto> sendNoti(@Valid @RequestBody NotiSendingRequestDto requestDto) {
        int userCount = useCase.sendNoti(requestDto.toCommand());
        return ResponseEntity.ok(new NotiSendingResponseDto(userCount));
    }
}
