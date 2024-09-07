package com.ilhwanlee.producer.notification.application.in;

import com.ilhwanlee.producer.notification.application.in.command.SubscribeNotiGroupCommand;
import com.ilhwanlee.producer.notification.application.in.command.SaveNotiGroupCommand;
import com.ilhwanlee.producer.notification.domain.NotiGroup;

public interface CrudNotiGroupUseCase {

    NotiGroup save(SaveNotiGroupCommand command);

    void subscribe(SubscribeNotiGroupCommand command);

    void unsubscribe(SubscribeNotiGroupCommand command);
}
