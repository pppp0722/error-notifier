package com.ilhwanlee.producer.notification.application.in.command;

import com.ilhwanlee.producer.notification.domain.Severity;
import java.util.List;

public record PublishNotiEventCommand(List<String> target, Severity severity, String message) {
}
