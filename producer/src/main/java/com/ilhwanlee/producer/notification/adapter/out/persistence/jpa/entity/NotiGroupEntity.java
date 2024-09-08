package com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity;

import com.ilhwanlee.producer.notification.domain.NotiGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "noti_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class NotiGroupEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(length = 300, nullable = false)
    private String desc;

    public static NotiGroupEntity fromDomain(NotiGroup notiGroup) {
        return NotiGroupEntity.builder()
                .name(notiGroup.getName())
                .desc(notiGroup.getDesc())
                .build();
    }

    public NotiGroup toDomain(List<UserEntity> userEntities) {
        return NotiGroup.builder()
                .id(id)
                .name(name)
                .desc(desc)
                .users(userEntities.stream()
                        .map(UserEntity::toDomain)
                        .toList())
                .build();
    }
}
