package com.ilhwanlee.producer.notification.adapter.out.persistence;

import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.common.web.exception.ErrorCode;
import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.NotiGroupEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.NotiGroupUserEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.UserEntity;
import com.ilhwanlee.producer.notification.adapter.out.persistence.repository.NotiGroupRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.repository.NotiGroupUserRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.repository.UserRepository;
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

    @Override
    public List<User> getTargetUsers(Target target) {
        if (target.isAll()) {
            return userRepository.findAll().stream().map(UserEntity::toDomain).toList();
        }

        Set<UUID> targetUserIds = new HashSet<>();
        List<User> targetUsers = new ArrayList<>();

        if (!target.userNames().isEmpty()) {
            List<User> users =
                    userRepository.findAllByNameIn(target.userNames()).stream().map(UserEntity::toDomain).toList();

            addUsersIfAbsent(targetUserIds, targetUsers, users);
        }

        if (!target.notiGroupNames().isEmpty()) {
            List<UUID> notiGroupIds = notiGroupRepository.findAllByNameIn(target.notiGroupNames()).stream()
                    .map(NotiGroupEntity::getId)
                    .toList();
            List<UUID> userIds = notiGroupUserRepository.findAllByNotiGroupIdIn(notiGroupIds).stream()
                    .map(NotiGroupUserEntity::getUserId)
                    .distinct()
                    .toList();
            List<User> users = userRepository.findAllByIdIn(userIds).stream()
                    .map(UserEntity::toDomain)
                    .toList();

            addUsersIfAbsent(targetUserIds, targetUsers, users);
        }

        return targetUsers;
    }

    private void addUsersIfAbsent(Set<UUID> targetUserIds, List<User> targetUsers, List<User> users) {
        for (User user : users) {
            if (!targetUserIds.contains(user.getId())) {
                targetUsers.add(user);
                targetUserIds.add(user.getId());
            }
        }
    }
}
