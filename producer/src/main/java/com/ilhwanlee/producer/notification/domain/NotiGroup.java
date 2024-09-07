package com.ilhwanlee.producer.notification.domain;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class NotiGroup {

    private UUID id;
    private String name;
    private String desc;
    private List<User> users;
}
