package com.ilhwanlee.producer.notification.adapter.in.dto;

import com.ilhwanlee.producer.notification.application.in.command.SaveNotiGroupCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotiGroupSaveRequestDto(
        @NotNull(message = "Noti group name must not be null.")
        @Size(min = 5, max = 30, message = "Noti group name must be between 5 and 30 characters.")
        String name,
        @NotNull(message = "Noti group desc must not be null.")
        @Size(min = 5, max = 200, message = "Noti group desc must be between 5 and 200 characters.")
        String desc
) {

    public SaveNotiGroupCommand toCommand() {
        return new SaveNotiGroupCommand(name, desc);
    }
}
