package com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.entity;

import com.ilhwanlee.producer.notification.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseEntity {

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @Column(length = 2048, nullable = false)
    private String token;

    @Column(length = 200, nullable = false)
    private String channelId;

    public User toDomain() {
        return User.builder()
                .id(id)
                .name(name)
                .token(token)
                .channelId(channelId)
                .build();
    }
}
