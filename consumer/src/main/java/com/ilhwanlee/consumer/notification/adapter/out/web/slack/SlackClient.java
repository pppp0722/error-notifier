package com.ilhwanlee.consumer.notification.adapter.out.web.slack;

import com.ilhwanlee.consumer.notification.adapter.out.web.dto.NotiSendingRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "slack")
public interface SlackClient {

    @PostMapping("/api/chat.postMessage")
    ResponseEntity<Void> sendMessage(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String authorization,
            @RequestBody NotiSendingRequestDto requestDto
    );
}
