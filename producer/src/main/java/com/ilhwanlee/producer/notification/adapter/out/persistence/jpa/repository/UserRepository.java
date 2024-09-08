package com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository;

import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findAllByNameIn(List<String> names);

    List<UserEntity> findAllByIdIn(List<UUID> ids);
}
