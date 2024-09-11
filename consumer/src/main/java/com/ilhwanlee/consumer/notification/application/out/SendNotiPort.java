package com.ilhwanlee.consumer.notification.application.out;

import com.ilhwanlee.common.domain.NotiInfo;
import reactor.core.publisher.Mono;

public interface SendNotiPort {

    Mono<Void> sendNoti(NotiInfo notiInfo);
}
