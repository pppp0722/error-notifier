package com.ilhwanlee.consumer.notification.application.out;

import com.ilhwanlee.common.domain.NotiInfo;

public interface SendNotiPort {

    void sendNoti(NotiInfo notiInfo);
}
