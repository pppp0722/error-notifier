package com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// N:M 중계 테이블
@Entity
@Table(name = "noti_group_user", uniqueConstraints = @UniqueConstraint(columnNames = {"noti_group_id", "user_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class NotiGroupUserEntity extends BaseEntity {

    @Column(name = "noti_group_id")
    UUID notiGroupId;

    @Column(name = "user_id")
    UUID userId;

    public static NotiGroupUserEntity of(UUID notiGroupId, UUID userId) {
        return builder()
                .notiGroupId(notiGroupId)
                .userId(userId)
                .build();
    }
}
