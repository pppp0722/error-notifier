package com.ilhwanlee.consumer.notification.adapter.out.web;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.notification.adapter.out.web.dto.NotiSendingRequestDto;
import com.ilhwanlee.consumer.notification.adapter.out.web.slack.SlackClient;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

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
