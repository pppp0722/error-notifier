package com.ilhwanlee.producer.common.web.filter.logging;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class HttpLog {

    private final String info;
    private final String header;
    private final String data;

    @Override
    public String toString() {
        return """
                %s
                header: %s
                data: %s
                """.formatted(info, header, data);
    }
}
