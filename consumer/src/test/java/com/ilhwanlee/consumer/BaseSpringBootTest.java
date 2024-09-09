package com.ilhwanlee.consumer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "spring.cloud.openfeign.client.config.slack.url=http://localhost:${wiremock.server.port}"
})
@ActiveProfiles("test")
public class BaseSpringBootTest {
}
