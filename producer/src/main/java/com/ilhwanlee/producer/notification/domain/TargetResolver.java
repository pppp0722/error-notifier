package com.ilhwanlee.producer.notification.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TargetResolver {

    public static Target resolve(List<String> target) {
        if (isAll(target)) {
            return Target.all();
        }

        List<String> userNames = new ArrayList<>();
        List<String> notiGroupNames = new ArrayList<>();

        for (String tag : target) {
            if (tag == null) {
                continue;
            }

            if (tag.length() >= 7 && tag.startsWith("@@")) {
                notiGroupNames.add(tag.substring(2));
            } else if (tag.length() >= 6 && tag.startsWith("@")) {
                userNames.add(tag.substring(1));
            }
        }

        return new Target(false, userNames, notiGroupNames);
    }

    private static boolean isAll(List<String> target) {
        return target.stream()
                .anyMatch("@all"::equals);
    }
}
