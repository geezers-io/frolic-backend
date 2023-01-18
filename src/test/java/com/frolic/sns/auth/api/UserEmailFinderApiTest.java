package com.frolic.sns.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frolic.sns.auth.application.finder.EmailFindManager;
import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class UserEmailFinderApiTest {

  @InjectMocks
  protected UserInfoFinderApi userInfoFinderApi;

  @Mock
  protected EmailFindManager emailFindManager;

  protected MockMvc mockMvc;

  protected ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void beforeEach() {
    mockMvc = MockMvcBuilders.standaloneSetup(userInfoFinderApi).build();
  }

  @Test
  @DisplayName("POST /auth/finder/email 정상 요청에 대해 처리 성공한다.")
  void sendCodeSuccessfully() throws Exception {
    // given
    UserFindEmailRequest request = new UserFindEmailRequest("01026554276");

    given(emailFindManager.sendAuthCode(request))
      .willReturn(UUID.randomUUID());

    // when
    ResultActions resultActions = mockMvc.perform(
      MockMvcRequestBuilders.post("/api/auth/finder/email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    ).andDo(print());

    // then
    resultActions.andExpect(status().isOk());
  }

}
