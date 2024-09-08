package com.ilhwanlee.producer.notification.adapter.out.queue.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilhwanlee.producer.common.util.LoggingUtils;
import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.common.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(String topic, Object object) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            LoggingUtils.logError("Unable to parse Object to JSON", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LoggingUtils.logError("Failed to produce message to Kafka topic.", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
