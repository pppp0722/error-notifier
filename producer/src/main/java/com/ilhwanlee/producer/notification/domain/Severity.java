package com.ilhwanlee.producer.notification.domain;

public enum Severity {
    HIGH, NORMAL, LOW;

    @Override
    public String toString() {
        return "[" + name().toLowerCase() + "]";
    }
}
