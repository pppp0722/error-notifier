package com.ilhwanlee.producer.notification.adapter.out.persistence.repository;

import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
