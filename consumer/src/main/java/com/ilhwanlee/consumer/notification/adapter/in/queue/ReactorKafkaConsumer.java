package com.ilhwanlee.consumer.notification.adapter.in.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.consumer.notification.application.in.SendNotiUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

/**
 * 에러 알림 전송 정보를 구독하는 Consumer
 */
@Component
@RequiredArgsConstructor
public class ReactorKafkaConsumer {

    private final SendNotiUseCase sendNotiUseCase;
    private final ReceiverOptions<String, NotiInfo> receiverOptions;
    private final ObjectMapper objectMapper;

    private void startConsuming() {
        KafkaReceiver.create(receiverOptions)
                .receive()
                .concatMap(this::processMessage)
                .subscribe();
    }

    private Mono<Void> processMessage(ReceiverRecord<String, NotiInfo> receiverRecord) {
        return sendNotiUseCase.sendNoti(receiverRecord.value())
                .doOnSuccess(unused -> {
                    receiverRecord.receiverOffset().acknowledge();
                    try {
                        LoggingUtils.logInfo("message", "Send noti was successful.",
                                "noti_info", objectMapper.writeValueAsString(receiverRecord.value()));
                    } catch (JsonProcessingException e) {
                        LoggingUtils.logError("message", "Can't mapping NotiInfo to String.");
                    }
                })
                .doOnError(e -> LoggingUtils.logError(
                        "message", "An error occurred while sending the message to the external service.",
                        "error", ExceptionUtils.getStackTrace(e)))
                .then();
    }
}
