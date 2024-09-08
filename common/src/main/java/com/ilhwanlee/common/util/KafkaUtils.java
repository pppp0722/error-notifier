package com.ilhwanlee.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaUtils {

    public static final String NOTI_TOPIC = "error-noti-topic";
    public static final String NOTI_GROUP = "error-noti-group";
}
