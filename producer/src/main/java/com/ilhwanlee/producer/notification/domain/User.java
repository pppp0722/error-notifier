package com.ilhwanlee.producer.notification.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class User {

    private UUID id;
    private String name;
    private String token;
    private String channelId;
}
