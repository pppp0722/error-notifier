package com.ilhwanlee.producer.notification.adapter.in.dto;

import com.ilhwanlee.producer.notification.application.in.command.SubscribeNotiGroupCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record NotiGroupSubscriptionRequestDto(
        @NotNull(message = "Group id must not be null.")
        UUID notiGroupId,
        @NotNull(message = "User id must not be null.")
        UUID userId
) {

    public SubscribeNotiGroupCommand toCommand() {
        return new SubscribeNotiGroupCommand(notiGroupId, userId);
    }
}
