package com.ilhwanlee.producer.notification.adapter.out.persistence;

import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.common.web.exception.ErrorCode;
import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.NotiGroupEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.NotiGroupUserEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.repository.NotiGroupRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.repository.NotiGroupUserRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.repository.UserRepository;
import com.ilhwanlee.producer.notification.application.out.CrudNotiGroupPort;
import com.ilhwanlee.producer.notification.domain.NotiGroup;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersistenceAdapter implements CrudNotiGroupPort {

    private final NotiGroupRepository notiGroupRepository;
    private final UserRepository userRepository;
    private final NotiGroupUserRepository notiGroupUserRepository;

    @Override
    public NotiGroup save(NotiGroup notiGroup) {
        if (notiGroupRepository.existsByName(notiGroup.getName())) {
            throw new CustomException(ErrorCode.NOTI_GROUP_NAME_EXISTS);
        }

        return notiGroupRepository.save(NotiGroupEntity.fromDomain(notiGroup)).toDomain(new ArrayList<>());
    }

    @Override
    public void subscribe(UUID notiGroupId, UUID userId) {
        if (!notiGroupRepository.existsById(notiGroupId)) {
            throw new CustomException(ErrorCode.UNKNOWN_NOTI_GROUP);
        }
        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.UNKNOWN_USER);
        }
        if (notiGroupUserRepository.existsByNotiGroupIdAndUserId(notiGroupId, userId)) {
            throw new CustomException(ErrorCode.SUBSCRIPTION_EXISTS);
        }
        notiGroupUserRepository.save(NotiGroupUserEntity.of(notiGroupId, userId));
    }

    @Override
    public void unsubscribe(UUID notiGroupId, UUID userId) {
        notiGroupUserRepository.deleteByNotiGroupIdAndUserId(notiGroupId, userId);
    }
}
