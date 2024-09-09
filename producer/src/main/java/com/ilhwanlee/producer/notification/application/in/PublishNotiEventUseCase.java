package com.ilhwanlee.producer.notification.application.in;

import com.ilhwanlee.producer.notification.application.in.command.PublishNotiEventCommand;

public interface PublishNotiEventUseCase {

    int publishNotiEvent(PublishNotiEventCommand command);
}
