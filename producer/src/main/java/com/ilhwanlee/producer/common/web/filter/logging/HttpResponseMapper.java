package com.ilhwanlee.producer.common.web.filter.logging;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class HttpResponseMapper {

    public static Map<String, String> toMap(ContentCachingResponseWrapper response, byte[] content) {
        Map<String, String> map = new LinkedHashMap<>();
        map.putAll(getInfoMap(response));
        map.putAll(getHeaderMap(response));
        map.putAll(getBodyMap(content));

        return map;
    }

    private static Map<String, String> getInfoMap(ContentCachingResponseWrapper response) {
        return Map.of("response_status", String.valueOf(response.getStatus()),
                "response_content_type", response.getContentType());
    }

    private static Map<String, String> getHeaderMap(ContentCachingResponseWrapper response) {
        String responseHeaders = response.getHeaderNames().stream()
                .map(headerName -> headerName + ":" + response.getHeader(headerName))
                .collect(Collectors.joining(", ", "[", "]"));

        return Map.of("response_headers", responseHeaders);
    }

    private static Map<String, String> getBodyMap(byte[] content) {
        return Map.of("response_body", new String(content, StandardCharsets.UTF_8));
    }
}
