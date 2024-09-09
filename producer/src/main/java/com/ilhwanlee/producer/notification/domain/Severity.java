package com.ilhwanlee.producer.notification.domain;

/**
 * 에러 위험도
 */
public enum Severity {
    HIGH, NORMAL, LOW;

    @Override
    public String toString() {
        return "[" + name().toLowerCase() + "]";
    }
}
