package com.ilhwanlee.producer.notification.domain;

import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 사용자 Domain
 * equals,hashCode 메서드는 name 필드로 오버라이딩되어 null 금지
 */
@Builder
@Getter
@EqualsAndHashCode(of = "name")
public class User {

    private UUID id;
    private String name;
    private String token;
    private String channelId;

    public User(UUID id, String name, String token, String channelId) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "User domain name field can't be null.");
        this.token = token;
        this.channelId = channelId;
    }
}
