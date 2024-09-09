package com.ilhwanlee.producer.notification.application;

import com.ilhwanlee.common.util.LoggingUtils;
import com.ilhwanlee.producer.notification.application.in.PublishNotiEventUseCase;
import com.ilhwanlee.producer.notification.application.in.command.PublishNotiEventCommand;
import com.ilhwanlee.producer.notification.application.out.CrudNotiGroupPort;
import com.ilhwanlee.producer.notification.application.out.PublishNotiEventPort;
import com.ilhwanlee.producer.notification.domain.Target;
import com.ilhwanlee.producer.notification.domain.TargetResolver;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class NotiEventPublishingService implements PublishNotiEventUseCase {

    private final CrudNotiGroupPort crudNotiGroupPort;
    private final PublishNotiEventPort publishNotiEventPort;

    @Override
    public int publishNotiEvent(PublishNotiEventCommand command) {
        Target target = TargetResolver.resolve(command.target());
        List<User> targetUsers = getTargetUsers(target);
        int targetUsersSize = targetUsers.size();

        if (targetUsersSize >= 1) {
            publishNotiEventPort.publishNotiEvent(targetUsers, command.severity(), command.message());
        }

        LoggingUtils.logInfo("message", "Publish noti event was successful.",
                "size", String.valueOf(targetUsersSize));

        return targetUsersSize;
    }

    private List<User> getTargetUsers(Target target) {
        if (target.hasTargetUsers()) {
            return crudNotiGroupPort.getTargetUsers(target);
        }

        return Collections.emptyList();
    }
}
