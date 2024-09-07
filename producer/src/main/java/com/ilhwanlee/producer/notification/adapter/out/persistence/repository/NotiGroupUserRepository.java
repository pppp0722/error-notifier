package com.ilhwanlee.producer.notification.adapter.out.persistence.repository;

import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.NotiGroupUserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiGroupUserRepository extends JpaRepository<NotiGroupUserEntity, UUID> {

    boolean existsByNotiGroupIdAndUserId(UUID notiGroupId, UUID userId);

    void deleteByNotiGroupIdAndUserId(UUID notiGroupId, UUID userId);
}
