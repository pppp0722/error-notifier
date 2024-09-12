package com.ilhwanlee.consumer.notification.adapter.out.web;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.BaseSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

class WebClientSendNotiAdapterTest extends BaseSpringBootTest {

    private static final NotiInfo NOTI_INFO =
            new NotiInfo("Bearer test_token", "test_channel_id", "test_message");


    @Autowired
    private WebClientSendNotiAdapter webClientSendNotiAdapter;

    @Test
    @DisplayName("알림 전송 API를 호출할 때 external service가 success 응답하면 Mono Void를 반환할 수 있다.")
    void sendNoti_whenExternalServiceResponseSuccess_returnMonoVoid() {
        // given
        stubFor(post(urlPathEqualTo("/api/chat.postMessage"))
                .willReturn(aResponse()
                        .withStatus(200)));

        // when
        webClientSendNotiAdapter.sendNoti(NOTI_INFO).block();

        // then
        verifyExternalServiceCalled();
    }

    @Test
    @DisplayName("알림 전송 API를 호출할 때 external service가 Client Error를 응답하면 Mono Error를 반환할 수 있다.")
    void sendNoti_whenExternalServiceResponseClientError_returnMonoError() {
        // given
        stubFor(post(urlPathEqualTo("/api/chat.postMessage"))
                .willReturn(aResponse()
                        .withStatus(400)));

        // when
        assertThatThrownBy(() -> webClientSendNotiAdapter.sendNoti(NOTI_INFO).block())
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("400");

        // then
        verifyExternalServiceCalled();
    }

    @Test
    @DisplayName("알림 전송 API를 호출할 때 external service가 Server Error를 응답하면 Mono Error를 반환할 수 있다.")
    void sendNoti_whenExternalServiceResponseServerError_returnMonoError() {
        // given
        stubFor(post(urlPathEqualTo("/api/chat.postMessage"))
                .willReturn(aResponse()
                        .withStatus(500)));

        // when
        assertThatThrownBy(() -> webClientSendNotiAdapter.sendNoti(NOTI_INFO).block())
                .isInstanceOf(HttpServerErrorException.class)
                .hasMessageContaining("500");

        // then
        verifyExternalServiceCalled();
    }

    private void verifyExternalServiceCalled() {
        verify(postRequestedFor(urlPathEqualTo("/api/chat.postMessage"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withHeader("Authorization", equalTo("Bearer " + NOTI_INFO.token()))
                .withRequestBody(matchingJsonPath("$.channel", equalTo(NOTI_INFO.channelId())))
                .withRequestBody(matchingJsonPath("$.text", equalTo(NOTI_INFO.message()))));
    }
}