package com.ilhwanlee.common.domain;

/**
 * Kafka 에러 알림 전송 데이터 DTO
 */
public record NotiInfo(String token, String channelId, String message) {
}