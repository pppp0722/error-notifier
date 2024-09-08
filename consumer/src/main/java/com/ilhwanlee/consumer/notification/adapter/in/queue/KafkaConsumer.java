package com.ilhwanlee.consumer.notification.adapter.in.queue;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.KafkaUtils;
import com.ilhwanlee.consumer.notification.application.in.SendNotiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SendNotiUseCase sendNotiUseCase;

    @KafkaListener(topics = KafkaUtils.NOTI_TOPIC, groupId = KafkaUtils.NOTI_GROUP)
    public void listen(NotiInfo notiInfo) {
        sendNotiUseCase.sendNoti(notiInfo);
    }
}
