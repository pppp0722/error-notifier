package com.ilhwanlee.producer.notification.application.in.command;

import java.util.UUID;

public record SubscribeNotiGroupCommand(UUID notiGroupId, UUID userId) {
}
