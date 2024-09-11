package com.ilhwanlee.consumer.notification.application;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.notification.application.in.SendNotiUseCase;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 에러 알림 전송을 위해 Slack API을 호출하기 위한 Service
 */
@Service
@RequiredArgsConstructor
public class NotiSendingService implements SendNotiUseCase {

    private final SendNotiPort sendNotiPort;

    @Override
    public Mono<Void> sendNoti(NotiInfo notiInfo) {
        return sendNotiPort.sendNoti(notiInfo);
    }
}
