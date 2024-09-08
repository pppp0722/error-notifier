package com.ilhwanlee.consumer.notification.adapter.in.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.KafkaUtils;
import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.consumer.notification.application.in.SendNotiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = KafkaUtils.NOTI_TOPIC, groupId = KafkaUtils.NOTI_GROUP)
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final SendNotiUseCase sendNotiUseCase;

    @KafkaHandler
    public void listen(String message) {
        try {
            NotiInfo notiInfo = objectMapper.readValue(message, NotiInfo.class);
            sendNotiUseCase.sendNoti(notiInfo);
        } catch (Exception e) {
            LoggingUtils.logError("", e);
        }
    }
}
