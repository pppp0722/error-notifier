package com.ilhwanlee.producer.common.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.logging.LogLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingUtils {

    private static final Logger logger = LogManager.getLogger(LoggingUtils.class);

    public static void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    public static void logInfo(String... keyValues) {
        log(LogLevel.INFO, keyValues);
    }

    public static void logWarn(String message) {
        log(LogLevel.WARN, message);
    }

    public static void logWarn(String... keyValues) {
        log(LogLevel.WARN, keyValues);
    }

    public static void logError(String... keyValues) {
        log(LogLevel.ERROR, keyValues);
    }

    public static void logError(String message, Exception e) {
        log(LogLevel.ERROR, "message", message, "error", Arrays.toString(e.getStackTrace()));
    }

    private static void log(LogLevel logLevel, String message) {
        log(logLevel, Map.of("message", message));
    }

    public static void log(LogLevel logLevel, String... keyValues) {
        if (keyValues.length % 2 != 0) {
            logWarn("The length of varargs must be even to output a log.");
            return;
        }

        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }

        log(logLevel, map);
    }

    public static void log(LogLevel logLevel, Map<String, String> map) {
        String message = map.entrySet().stream()
                .map(e -> String.format("\"%s\"=\"%s\"", e.getKey(), e.getValue() != null ? e.getValue() : ""))
                .collect(Collectors.joining(" "));

        logInternal(logLevel, message);
    }

    private static void logInternal(LogLevel logLevel, String message) {
        switch (logLevel) {
            case WARN -> logger.warn(message);
            case ERROR -> logger.error(message);
            default -> logger.info(message);
        }
    }
}
