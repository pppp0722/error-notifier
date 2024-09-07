package com.ilhwanlee.producer.notification.adapter.in;

import com.ilhwanlee.producer.BaseSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
class NotiGroupCrudControllerTest extends BaseSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("NotiGroup을 생성할 수 있다.")
    void save_whenInputIsValid_responseCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test_name\",\"desc\":\"test_desc\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("test_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.desc").value("test_desc"));
    }

    @ParameterizedTest
    @CsvSource({
            "{\"name\":\"test_name1\"}",
            "{\"desc\":\"test_desc\"}",
            "'{\"name\":\"name\",\"desc\":\"test_desc\"}'",
            "'{\"name\":\"test_name\",\"desc\":\"desc\"}'"
    })
    @DisplayName("NotiGroup을 생성할 때 잘못된 요청은 400을 응답할 수 있다.")
    void save_whenInputIsInvalid_responseBadRequest(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("NotiGroup을 생성할 때 notiGroupId가 중복되면 409를 응답할 수 있다.")
    void save_whenIdIsExists_responseConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test_name1\", \"desc\":\"test_desc\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("NotiGroup에 User가 가입할 수 있다.")
    void subscribe_whenInputIsValid_responseCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"notiGroupId\":\"22222222-2222-2222-2222-222222222222\","
                                + "\"userId\":\"22222222-2222-2222-2222-222222222222\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "'{\"notiGroupId\":\"invalid format\","
                    + "\"userId\":\"22222222-2222-2222-2222-222222222222\"}'",
            "'{\"notiGroupId\":\"22222222-2222-2222-2222-222222222222\","
                    + "\"userId\":\"invalid format\"}'"
    })
    @DisplayName("NotiGroup에 User가 가입할 때 잘못된 요청은 400을 응답할 수 있다.")
    void subscribe_whenInputIsInvalid_responseBadRequest(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({
            "'{\"notiGroupId\":\"00000000-0000-0000-0000-000000000000\","
                    + "\"userId\":\"22222222-2222-2222-2222-222222222222\"}'",
            "'{\"notiGroupId\":\"22222222-2222-2222-2222-222222222222\","
                    + "\"userId\":\"00000000-0000-0000-0000-000000000000\"}'",
    })
    @DisplayName("NotiGroup에 User가 가입할 때 알려지지 않은 요청은 404를 응답할 수 있다.")
    void subscribe_whenInputIsUnknown_responseNotFound(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("NotiGroup에 User가 가입할 때 notiGroupId, userId가 중복되면 409를 응답할 수 있다.")
    void subscribe_whenIdsAreExist_responseConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/noti-groups/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"notiGroupId\":\"11111111-1111-1111-1111-111111111111\","
                                + "\"userId\":\"11111111-1111-1111-1111-111111111111\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("NotiGroup에 User가 탈퇴할 수 있다.")
    void unsubscribe_whenInputIsValid_responseOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/noti-groups/unsubscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"notiGroupId\":\"11111111-1111-1111-1111-111111111111\","
                                + "\"userId\":\"11111111-1111-1111-1111-111111111111\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            "'{\"notiGroupId\":\"invalid format\","
                    + "\"userId\":\"22222222-2222-2222-2222-222222222222\"}'",
            "'{\"notiGroupId\":\"22222222-2222-2222-2222-222222222222\","
                    + "\"userId\":\"invalid format\"}'"
    })
    @DisplayName("NotiGroup에 User가 탈퇴할 때 잘못된 요청은 400을 응답할 수 있다.")
    void unsubscribe_whenInputIsInvalid_responseBadRequest(String content) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/noti-groups/unsubscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
