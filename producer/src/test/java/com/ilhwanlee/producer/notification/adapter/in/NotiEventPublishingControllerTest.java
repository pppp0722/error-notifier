package com.ilhwanlee.producer.notification.adapter.in;

import com.ilhwanlee.producer.BaseSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, ports = 9093)
class NotiEventPublishingControllerTest extends BaseSpringBootTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "'{\"target\":[\"@test_name2\",\"@@test_name1\"],\"severity\":\"normal\",\"message\":\"test message\"}', 2",
            "'{\"target\":[\"@@test_name1\"],\"severity\":\"high\",\"message\":\"test message\"}', 1",
            "'{\"target\":[\"@all\", \"@test_name1\"],\"severity\":\"low\",\"message\":\"test message\"}', 2",
            "'{\"target\":[\"@unknown_name\"],\"severity\":\"low\",\"message\":\"test message\"}', 0",
            "'{\"target\":[\"@@unknown_name\"],\"severity\":\"low\",\"message\":\"test message\"}', 0",
    })
    @DisplayName("발송 대상을 조회하여 Queue에 넣을 수 있다.")
    void save_whenInputIsValid_responseOk(String content, int count) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/alerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userCount").value(count));
    }

    @ParameterizedTest
    @CsvSource({
            "'{\"severity\":\"normal\",\"message\":\"test message\"}'",
            "'{\"target\":[],\"severity\":\"normal\",\"message\":\"test message\"}'",
            "'{\"target\":[\"@@test_name1\"],\"message\":\"test message\"}'",
            "'{\"target\":[\"@@test_name1\"],\"severity\":\"unknown_severity\",\"message\":\"test message\"}'",
            "'{\"target\":[\"@all\", \"@test_name1\"],\"severity\":\"low\"}'"
    })
    @DisplayName("발송 대상을 조회하여 Queue에 넣을 때 잘못된 요청은 400을 응답할 수 있다.")
    void save_whenInputIsInvalid_responseBadRequest(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/alerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
