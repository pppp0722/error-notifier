package com.ilhwanlee.producer.notification.adapter.out.persistence;

import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.common.web.exception.ErrorCode;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity.NotiGroupEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity.NotiGroupUserEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity.UserEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository.NotiGroupRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository.NotiGroupUserRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository.UserRepository;
import com.ilhwanlee.producer.notification.application.out.CrudNotiGroupPort;
import com.ilhwanlee.producer.notification.domain.NotiGroup;
import com.ilhwanlee.producer.notification.domain.Target;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 알림 그룹 생성, 가입 및 탈퇴 데이터 저장 및 알림 전송 대상 정보 조회를 위한 영속성 Adapter
 */
@Component
@RequiredArgsConstructor
class PersistenceAdapter implements CrudNotiGroupPort {

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

    @Override
    public List<User> getTargetUsers(Target target) {
        if (target.isAllUsers()) {
            return userRepository.findAll().stream().map(UserEntity::toDomain).toList();
        }

        List<String> usernames = target.getUsernames();
        List<String> notiGroupNames = target.getNotiGroupNames();
        Set<UUID> targetUserIds = new HashSet<>();
        List<User> targetUsers = new ArrayList<>();

        if (!usernames.isEmpty()) {
            List<UserEntity> userEntities = userRepository.findAllByNameIn(usernames);
            addUsersIfAbsent(targetUserIds, targetUsers, userEntities);
        }

        if (!notiGroupNames.isEmpty()) {
            List<UUID> notiGroupIds = notiGroupRepository.findAllByNameIn(notiGroupNames).stream()
                    .map(NotiGroupEntity::getId)
                    .toList();
            List<UUID> userIds = notiGroupUserRepository.findAllByNotiGroupIdIn(notiGroupIds).stream()
                    .map(NotiGroupUserEntity::getUserId)
                    .distinct()
                    .toList();
            List<UserEntity> userEntities = userRepository.findAllByIdIn(userIds);
            addUsersIfAbsent(targetUserIds, targetUsers, userEntities);
        }

        return targetUsers;
    }

    private void addUsersIfAbsent(Set<UUID> targetUserIds, List<User> targetUsers, List<UserEntity> userEntities) {
        for (UserEntity userEntity : userEntities) {
            if (!targetUserIds.contains(userEntity.getId())) {
                targetUsers.add(userEntity.toDomain());
                targetUserIds.add(userEntity.getId());
            }
        }
    }
}
