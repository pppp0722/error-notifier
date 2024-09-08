package com.ilhwanlee.producer.common.web.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {

    // 4XX
    BAD_REQUEST(400, "Bad Request",
            "The request is invalid or cannot be processed."),
    INVALID_SEVERITY(400, "Bad Request",
            "Severity should be one of the following values: high, normal, low."),
    UNKNOWN_NOTI_GROUP(404, "Not Found",
            "The requested noti group does not exist."),
    UNKNOWN_USER(404, "Not Found",
            "The requested user does not exist."),
    NOTI_GROUP_NAME_EXISTS(409, "Conflict", "Noti group name already exists."),
    SUBSCRIPTION_EXISTS(409, "Conflict", "Subscription already exists."),

    // 5XX
    INTERNAL_SERVER_ERROR(500, "Internal Server Error",
            "An internal server error occurred.");

    private final int httpCode;
    private final String httpMessage;
    private final String moreInformation;
}
