package com.ilhwanlee.consumer.notification.adapter.out.web;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.notification.adapter.out.web.dto.NotiSendingRequestDto;
import com.ilhwanlee.consumer.notification.adapter.out.web.slack.SlackClient;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * 에러 알림 전송을 위해 Slack API를 호출하기 위한 Adapter
 */
@Component
@RequiredArgsConstructor
public class RestAdapter implements SendNotiPort {

    private final SlackClient slackClient;

    @Override
    public void sendNoti(NotiInfo notiInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + notiInfo.token());

        slackClient.sendMessage(headers, new NotiSendingRequestDto(notiInfo.channelId(), notiInfo.message()));
    }
}
