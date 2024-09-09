package com.ilhwanlee.producer.notification.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TargetResolverTest {

    @Test
    @DisplayName("target을 받아 User와 NotiGroup으로 분리할 수 있다.")
    void resolve_whenNotContainsAll_returnNames() {
        // given
        List<String> targetList = List.of("@username1", "@username2", "@@noti_group_name1");

        // when
        Target target = TargetResolver.resolve(targetList);

        // then
        assertThat(target.isAllUsers()).isFalse();
        assertThat(target.getUsernames()).hasSize(2);
        assertThat(target.getNotiGroupNames()).hasSize(1);
    }

    @Test
    @DisplayName("target을 받아 user와 group으로 분리할 수 있다.")
    void resolve_whenContainsAll_returnAll() {
        // given
        List<String> targetList = List.of("@username1", "@username2", "@@noti_group_name1", "@all");

        // when
        Target target = TargetResolver.resolve(targetList);

        // then
        assertThat(target.isAllUsers()).isTrue();
        assertThat(target.getUsernames()).isEmpty();
        assertThat(target.getNotiGroupNames()).isEmpty();
    }
}