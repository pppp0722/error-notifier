package com.ilhwanlee.consumer.notification.adapter.out.web;

import com.ilhwanlee.common.domain.NotiInfo;
import com.ilhwanlee.consumer.notification.adapter.out.web.dto.NotiSendingRequestDto;
import com.ilhwanlee.consumer.notification.adapter.out.web.slack.SlackClient;
import com.ilhwanlee.consumer.notification.application.out.SendNotiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestAdapter implements SendNotiPort {

    private final SlackClient slackClient;

    @Override
    public void sendNoti(NotiInfo notiInfo) {
        slackClient.sendMessage(
                MediaType.APPLICATION_JSON_VALUE,
                "Bearer " + notiInfo.token(),
                new NotiSendingRequestDto(notiInfo.channelId(), notiInfo.message())
        );
    }
}
