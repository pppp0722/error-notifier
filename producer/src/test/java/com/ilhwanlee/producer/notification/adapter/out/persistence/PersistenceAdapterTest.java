package com.ilhwanlee.producer.notification.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ilhwanlee.producer.BaseSpringBootTest;
import com.ilhwanlee.producer.common.web.exception.CustomException;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository.NotiGroupRepository;
import com.ilhwanlee.producer.notification.adapter.out.persistence.jpa.repository.NotiGroupUserRepository;
import com.ilhwanlee.producer.notification.domain.NotiGroup;
import com.ilhwanlee.producer.notification.domain.Target;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PersistenceAdapterTest extends BaseSpringBootTest {

    @Autowired
    private PersistenceAdapter persistenceAdapter;

    @Autowired
    private NotiGroupRepository notiGroupRepository;

    @Autowired
    private NotiGroupUserRepository notiGroupUserRepository;

    @Test
    @DisplayName("NotiGroup을 저장할 수 있다.")
    void save_whenInputIsValid_saveNotiGroup() {
        // given
        NotiGroup notiGroup = NotiGroup.builder().name("save_test").desc("test_desc").build();

        // when
        NotiGroup result = persistenceAdapter.save(notiGroup);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(notiGroupRepository.existsByName(notiGroup.getName())).isTrue();
    }

    @Test
    @DisplayName("NotiGroup을 저장할 때 name이 중복되면 CustomException을 던질 수 있다.")
    void save_whenNotiGroupNameExists_throwCustomException() {
        // given
        NotiGroup notiGroup = NotiGroup.builder().name("group_name1").desc("test_desc").build();

        // when & then
        assertThatThrownBy(() -> persistenceAdapter.save(notiGroup))
                .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("User가 NotiGroup에 가입할 수 있다.")
    void subscribe_whenInputIsValid_saveNotiGroupUsers() {
        // given
        String id = "22222222-2222-2222-2222-222222222222";
        UUID notiGroupId = UUID.fromString(id);
        UUID userId = UUID.fromString(id);

        // when
        persistenceAdapter.subscribe(notiGroupId, userId);

        // then
        assertThat(notiGroupUserRepository.existsByNotiGroupIdAndUserId(notiGroupId, userId)).isTrue();
    }

    @Test
    @DisplayName("대상 User List를 조회할 때 모든 사용자를 조회할 수 있다.")
    void getTargetUsers_whenInputIsAll_getAllUsers() {
        // given
        Target target = Target.ofAllUsers();

        // when
        List<User> users = persistenceAdapter.getTargetUsers(target);

        // then
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("대상 User List를 조회할 때 userNames로 조회할 수 있다.")
    void getTargetUsers_whenInputIsUserNames_getTargetUsers() {
        // given
        Target target = new Target(false, List.of("user_name1"), Collections.emptyList());

        // when
        List<User> users = persistenceAdapter.getTargetUsers(target);

        // then
        assertThat(users).hasSize(1);
    }

    @Test
    @DisplayName("대상 User List를 조회할 때 notiGroupNames 조회할 수 있다.")
    void getTargetUsers_whenInputIsNotiGroupNames_getTargetUsers() {
        // given
        Target target = new Target(false, Collections.emptyList(), List.of("group_name1"));

        // when
        List<User> users = persistenceAdapter.getTargetUsers(target);

        // then
        assertThat(users).hasSize(1);
    }
}