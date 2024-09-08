package com.ilhwanlee.producer.notification.adapter.out.queue.kafka;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.common.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, NotiInfo> kafkaTemplate;

    public void sendMessage(String topic, NotiInfo notiInfo) {
        try {
            kafkaTemplate.send(topic, notiInfo);
        } catch (Exception e) {
            LoggingUtils.logError("Failed to produce message to Kafka topic.", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
