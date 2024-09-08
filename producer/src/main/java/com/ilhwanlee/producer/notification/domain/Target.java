package com.ilhwanlee.producer.notification.domain;

import java.util.ArrayList;
import java.util.List;

public record Target(boolean isAll, List<String> userNames, List<String> notiGroupNames) {

    public static Target all() {
        return new Target(true, new ArrayList<>(), new ArrayList<>());
    }
}
