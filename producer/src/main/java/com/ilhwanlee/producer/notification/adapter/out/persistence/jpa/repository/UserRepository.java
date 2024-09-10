package com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository;

import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findAllByNameIn(List<String> names);

    List<UserEntity> findAllByIdIn(List<UUID> ids);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN NotiGroupUserEntity ngu ON u.id = ngu.userId " +
            "JOIN NotiGroupEntity ng ON ngu.notiGroupId = ng.id " +
            "WHERE ng.name IN :notiGroupNames")
    List<UserEntity> findAllByNotiGroupNameIn(@Param("notiGroupNames") List<String> notiGroupNames);
}
