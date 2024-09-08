package com.ilhwanlee.producer.notification.adapter.out.persistence.repository;

import com.ilhwanlee.producer.notification.adapter.out.persistence.entity.NotiGroupEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiGroupRepository extends JpaRepository<NotiGroupEntity, UUID> {

    boolean existsByName(String name);

    List<NotiGroupEntity> findAllByNameIn(List<String> names);
}
