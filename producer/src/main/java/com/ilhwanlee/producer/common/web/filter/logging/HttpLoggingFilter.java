package com.ilhwanlee.producer.common.web.filter.logging;

import com.ilhwanlee.common.util.LoggingUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * 트러블슈팅을 위해 Request 및 Response를 logging하는 Filter
 */
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {

        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(request, response);

        byte[] content = response.getContentAsByteArray();
        response.copyBodyToResponse();

        Map<String, String> logMap = new LinkedHashMap<>();
        logMap.putAll(HttpRequestMapper.toMap(request));
        logMap.putAll(HttpResponseMapper.toMap(response, content));

        LoggingUtils.log(LogLevel.INFO, logMap);
    }
}
