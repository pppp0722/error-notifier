package com.ilhwanlee.producer.notification.application.in;

import com.ilhwanlee.producer.notification.application.in.command.SendNotiCommand;

public interface SendNotiUseCase {

    int sendNoti(SendNotiCommand command);
}
