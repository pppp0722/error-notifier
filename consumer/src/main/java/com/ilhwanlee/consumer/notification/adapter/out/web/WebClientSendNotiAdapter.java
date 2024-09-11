package com.ilhwanlee.consumer.notification.adapter.out.web;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.common.properties.UrlProperties;
import com.ilhwanlee.consumer.notification.adapter.out.web.dto.NotiSendingRequestDto;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import java.io.IOException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 에러 알림 전송을 위해 Slack API를 호출하기 위한 Adapter
 */
@Component
public class WebClientSendNotiAdapter implements SendNotiPort {

    private final WebClient webClient;

    public WebClientSendNotiAdapter(WebClient.Builder webClientBuilder, UrlProperties urlProperties) {
        this.webClient = webClientBuilder.baseUrl(urlProperties.slack()).build();
    }

    @Override
    public Mono<Void> sendNoti(NotiInfo notiInfo) {
        return webClient.post()
                .uri("/api/chat.postMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + notiInfo.token())
                .bodyValue(new NotiSendingRequestDto(notiInfo.channelId(), notiInfo.message()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBdoy -> Mono.error(new IOException(errorBdoy))))
                .bodyToMono(Void.class)
                .doOnError(Mono::error);
    }
}
