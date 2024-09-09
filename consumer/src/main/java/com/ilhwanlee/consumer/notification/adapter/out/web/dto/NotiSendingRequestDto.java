package com.ilhwanlee.consumer.notification.adapter.out.web.dto;

/**
 * Slack API 호출을 위한 Request DTO
 */
public record NotiSendingRequestDto(String channel, String text) {
}
