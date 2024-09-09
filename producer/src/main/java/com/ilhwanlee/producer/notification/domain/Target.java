package com.ilhwanlee.producer.notification.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Target {

    private boolean isAllUsers;
    private List<String> usernames;
    private List<String> notiGroupNames;

    public static Target ofAllUsers() {
        return new Target(true, new ArrayList<>(), new ArrayList<>());
    }

    public boolean hasTargetUsers() {
        return isAllUsers || !usernames.isEmpty() || !notiGroupNames.isEmpty();
    }
}
