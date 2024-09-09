package com.ilhwanlee.producer.notification.adapter.out.queue;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.KafkaUtils;
import com.ilhwanlee.producer.notification.adapter.out.queue.kafka.KafkaProducer;
import com.ilhwanlee.producer.notification.application.out.PublishNotiEventPort;
import com.ilhwanlee.producer.notification.domain.Severity;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 장애 알림 Message를 발행하기 위한 Message Queue Adapter
 */
@Component
@RequiredArgsConstructor
class EventQueueAdapter implements PublishNotiEventPort {

    private final KafkaProducer kafkaProducer;

    @Override
    public void publishNotiEvent(List<User> users, Severity severity, String message) {
        String errorMessage = severity + " " + message;
        users.stream()
                .map(user -> new NotiInfo(user.getToken(), user.getChannelId(), errorMessage))
                .forEach(notiInfo -> kafkaProducer.sendMessage(KafkaUtils.NOTI_TOPIC, notiInfo));
    }
}