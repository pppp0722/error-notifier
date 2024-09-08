package com.ilhwanlee.producer.notification.application.out;

import com.ilhwanlee.producer.notification.domain.NotiGroup;
import com.ilhwanlee.producer.notification.domain.Target;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.List;
import java.util.UUID;

public interface CrudNotiGroupPort {

    NotiGroup save(NotiGroup notiGroup);

    void subscribe(UUID notiGroupId, UUID userId);

    void unsubscribe(UUID notiGroupId, UUID userId);

    List<User> getTargetUsers(Target target);
}
