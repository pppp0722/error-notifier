package com.ilhwanlee.consumer.notification.adapter.in.queue;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.BaseSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class KafkaConsumerTest extends BaseSpringBootTest {

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Test
    @DisplayName("message를 구독하여 Slack API를 호출할 수 있다.")
    void listen_whenMessageIsConsumed_callSlackApi() {
        // given
        String slackApiUri = "/api/chat.postMessage";

        stubFor(post(urlEqualTo(slackApiUri))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody("{\"result\":\"success\"}")));

        NotiInfo notiInfo = new NotiInfo("token", "channel_id", "message");

        // when
        kafkaConsumer.listen(notiInfo);

        // then
        verify(postRequestedFor(urlEqualTo(slackApiUri))
                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
                .withHeader("Authorization", equalTo("Bearer token"))
                .withRequestBody(equalToJson("{\"channel\":\"channel_id\",\"text\":\"message\"}")));
    }
}