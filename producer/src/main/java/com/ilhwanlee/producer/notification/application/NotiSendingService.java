package com.ilhwanlee.producer.notification.application;

import com.ilhwanlee.producer.notification.application.in.SendNotiUseCase;
import com.ilhwanlee.producer.notification.application.in.command.SendNotiCommand;
import com.ilhwanlee.producer.notification.application.out.CrudNotiGroupPort;
import com.ilhwanlee.producer.notification.application.out.EnqueueNotiPort;
import com.ilhwanlee.producer.notification.domain.Target;
import com.ilhwanlee.producer.notification.domain.TargetResolver;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotiSendingService implements SendNotiUseCase {

    private final CrudNotiGroupPort crudNotiGroupPort;
    private final EnqueueNotiPort enqueueNotiPort;

    @Override
    public int sendNoti(SendNotiCommand command) {
        Target target = TargetResolver.resolve(command.target());
        List<User> targetUsers = crudNotiGroupPort.getTargetUsers(target);
        if (!targetUsers.isEmpty()) {
            enqueueNotiPort.enqueueNoti(targetUsers, command.severity(), command.message());
        }
        return targetUsers.size();
    }
}
