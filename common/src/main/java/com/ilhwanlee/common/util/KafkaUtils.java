package com.ilhwanlee.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Kafka 토픽명, 그룹ID Util Class
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaUtils {

    public static final String NOTI_TOPIC = "error-noti-topic";
    public static final String NOTI_GROUP = "error-noti-group";
}
