package com.ilhwanlee.producer.notification.application;

import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.producer.notification.application.in.CrudNotiGroupUseCase;
import com.ilhwanlee.producer.notification.application.in.command.SaveNotiGroupCommand;
import com.ilhwanlee.producer.notification.application.in.command.SubscribeNotiGroupCommand;
import com.ilhwanlee.producer.notification.application.out.CrudNotiGroupPort;
import com.ilhwanlee.producer.notification.domain.NotiGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 알림 그룹 생성, 가입 및 탈퇴 CRUD Service
 */
@Service
@Transactional
@RequiredArgsConstructor
class NotiGroupCrudService implements CrudNotiGroupUseCase {

    private final CrudNotiGroupPort crudNotiGroupPort;

    @Override
    public NotiGroup save(SaveNotiGroupCommand command) {
        NotiGroup notiGroup = NotiGroup.builder()
                .name(command.name())
                .desc(command.desc())
                .build();
        notiGroup = crudNotiGroupPort.save(notiGroup);

        LoggingUtils.logInfo("message", "Saving noti group was successful.",
                "notiGroupId", notiGroup.getId().toString(),
                "notiGroupName", notiGroup.getName());

        return notiGroup;
    }

    @Override
    public void subscribe(SubscribeNotiGroupCommand command) {
        crudNotiGroupPort.subscribe(command.notiGroupId(), command.userId());

        LoggingUtils.logInfo("message", "Subscription to noti group was successful.",
                "notiGroupId", command.notiGroupId().toString(),
                "userId", command.userId().toString());
    }

    @Override
    public void unsubscribe(SubscribeNotiGroupCommand command) {
        crudNotiGroupPort.unsubscribe(command.notiGroupId(), command.userId());

        LoggingUtils.logInfo("message", "Unsubscription to noti group was successful.",
                "notiGroupId", command.notiGroupId().toString(),
                "userId", command.userId().toString());
    }
}
