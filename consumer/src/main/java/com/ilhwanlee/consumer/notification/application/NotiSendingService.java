package com.ilhwanlee.consumer.notification.application;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.consumer.notification.application.in.SendNotiUseCase;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 에러 알림 전송을 위해 Slack API을 호출하기 위한 Service
 */
@Service
@RequiredArgsConstructor
public class NotiSendingService implements SendNotiUseCase {

    private final SendNotiPort sendNotiPort;

    @Override
    public void sendNoti(NotiInfo notiInfo) {
        sendNotiPort.sendNoti(notiInfo);
        LoggingUtils.logInfo("message", "Send noti was successful.");
    }
}
