package com.ilhwanlee.consumer.notification.application;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.consumer.notification.application.in.SendNotiUseCase;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
