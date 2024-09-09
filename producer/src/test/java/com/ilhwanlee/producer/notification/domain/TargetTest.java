package com.ilhwanlee.producer.notification.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TargetTest {

    @Test
    @DisplayName("target User 존재 여부를 확인할 때 isAllUsers면 true를 반환할 수 있다.")
    void hasTargetUsers_allUsersTarget_returnTrue() {
        // given
        Target allUsersTarget = Target.ofAllUsers();
        assertHasTargetUsers(allUsersTarget, true);
    }

    @Test
    @DisplayName("target User 존재 여부를 확인할 때 usernames에 원소가 존재하면 true를 반환할 수 있다.")
    void hasTargetUsers_usersTarget_returnTrue() {
        // given
        Target usersTarget = new Target(false, List.of("username"), Collections.emptyList());
        assertHasTargetUsers(usersTarget, true);
    }

    @Test
    @DisplayName("target User 존재 여부를 확인할 때 notiGroupNames에 원소가 존재하면 true를 반환할 수 있다.")
    void hasTargetUsers_notiGroupsTarget_returnTrue() {
        // given
        Target notiGroupsTarget = new Target(false, Collections.emptyList(), List.of("group_name"));
        assertHasTargetUsers(notiGroupsTarget, true);

    }

    @Test
    @DisplayName("target User 존재 여부를 확인할 때 target이 없는 경우 false를 반환할 수 있다.")
    void hasTargetUsers_noTarget_returnFalse() {
        // given
        Target noTarget = new Target(false, Collections.emptyList(), Collections.emptyList());
        assertHasTargetUsers(noTarget, false);
    }

    private void assertHasTargetUsers(Target target, boolean expected) {
        // when
        boolean hasTargetUsers = target.hasTargetUsers();

        // then
        assertThat(hasTargetUsers).isEqualTo(expected);
    }
}