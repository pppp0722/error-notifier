package com.ilhwanlee.producer.notification.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ["@all", "@username1", "@@noti_group_name1"] 과 같은 target 정보에서
 * 전체 대상 여부, userNames, notiGroupNames로 분류한 Target을 생성하기 위한 Class
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetResolver {

    private static final int MIN_USERNAME_LENGTH_WITH_TAG = 6;
    private static final int MIN_NOTI_GROUP_NAME_LENGTH_WITH_TAG = 7;
    private static final String USER_TAG = "@";
    private static final String NOTI_GROUP_TAG = "@@";
    private static final String ALL_TAG = "@all";

    public static Target resolve(List<String> target) {
        // target에 "@all"이 포함되어 있으면 전체 대상
        if (isAllUsers(target)) {
            return Target.ofAllUsers();
        }

        List<String> usernames = new ArrayList<>();
        List<String> notiGroupNames = new ArrayList<>();

        for (String tag : target) {
            if (tag == null) {
                continue;
            }

            // "@@"이 포함되어 있으면 notiGroupNames, "@"이 포함되어 있으면 usernames로 분류
            if (tag.length() >= MIN_USERNAME_LENGTH_WITH_TAG && tag.startsWith(NOTI_GROUP_TAG)) {
                notiGroupNames.add(tag.substring(NOTI_GROUP_TAG.length()));
            } else if (tag.length() >= MIN_NOTI_GROUP_NAME_LENGTH_WITH_TAG && tag.startsWith(USER_TAG)) {
                usernames.add(tag.substring(USER_TAG.length()));
            }
        }

        return new Target(false, usernames, notiGroupNames);
    }

    private static boolean isAllUsers(List<String> target) {
        return target.stream().anyMatch(ALL_TAG::equals);
    }
}
