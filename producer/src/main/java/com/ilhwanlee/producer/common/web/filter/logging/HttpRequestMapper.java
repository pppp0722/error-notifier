package com.ilhwanlee.producer.common.web.filter.logging;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class HttpRequestMapper {

    public static Map<String, String> toMap(ContentCachingRequestWrapper request) {
        Map<String, String> map = new LinkedHashMap<>();
        map.putAll(getInfoMap(request));
        map.putAll(getHeaderMap(request));
        map.putAll(getBodyMap(request));

        return map;
    }

    private static Map<String, String> getInfoMap(ContentCachingRequestWrapper request) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();

        if (StringUtils.isNotEmpty(queryString)) {
            requestUri += "?" + request.getQueryString();
        }

        return Map.of(
                "request_method", request.getMethod(),
                "request_uri", requestUri,
                "request_content_type", StringUtils.defaultString(request.getContentType())
        );
    }

    private static Map<String, String> getHeaderMap(ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder sb = new StringBuilder("[");

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            sb.append(headerName).append(":").append(request.getHeader(headerName));

            if (headerNames.hasMoreElements()) {
                sb.append(",");
            }
        }

        return Map.of("request_headers", sb.toString());
    }

    private static Map<String, String> getBodyMap(ContentCachingRequestWrapper request) {
        return Map.of("request_body", new String(request.getContentAsByteArray(), StandardCharsets.UTF_8));
    }
}
