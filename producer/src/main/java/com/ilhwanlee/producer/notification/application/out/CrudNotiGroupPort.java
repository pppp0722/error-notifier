package com.ilhwanlee.producer.notification.application.out;

import com.ilhwanlee.producer.notification.domain.NotiGroup;
import java.util.UUID;

public interface CrudNotiGroupPort {

    NotiGroup save(NotiGroup notiGroup);

    void subscribe(UUID notiGroupId, UUID userId);

    void unsubscribe(UUID notiGroupId, UUID userId);
}
