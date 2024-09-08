package com.ilhwanlee.consumer.common.config;

import com.ilhwanlee.common.util.LoggingUtils;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

@Configuration
public class FeignClientConfig {

    @Bean
    ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            LoggingUtils.logError(
                    "message", "An error occurred during the request.",
                    "response_status", String.valueOf(response.status()),
                    "response_body", readResponseBody(response)
            );

            return new RuntimeException();
        };
    }

    private String readResponseBody(Response response) {
        String responseBody = null;
        try (InputStream inputStream = response.body().asInputStream()) {
            responseBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) { /* no-op */ }
        return responseBody;
    }
}
