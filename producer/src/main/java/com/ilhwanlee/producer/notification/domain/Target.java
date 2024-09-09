package com.ilhwanlee.producer.notification.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 알림 전송 대상자를 조회하기 위해 target 정보를 식별한 Class
 */
@AllArgsConstructor
@Getter
public class Target {

    private boolean isAllUsers;
    private List<String> usernames;
    private List<String> notiGroupNames;

    public static Target ofAllUsers() {
        // 전체 대상 여부가 true인 Target을 생성하기 위한 정적 팩토리 메서드
        return new Target(true, new ArrayList<>(), new ArrayList<>());
    }

    public boolean hasTargetUsers() {
        // isAllUsers가 true이거나 usernames, notiGroupNames에 하나라도 원소가 있으면 target이 존재
        return isAllUsers || !usernames.isEmpty() || !notiGroupNames.isEmpty();
    }
}
