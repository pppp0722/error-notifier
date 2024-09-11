package com.ilhwanlee.consumer.notification.application.in;

import com.ilhwanlee.common.domain.NotiInfo;
import reactor.core.publisher.Mono;

public interface SendNotiUseCase {

    Mono<Void> sendNoti(NotiInfo notiInfo);
}
