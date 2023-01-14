package com.frolic.sns.auth.api;

import com.frolic.sns.auth.application.EmailFindManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthSendSMSApiTest {

  @InjectMocks
  protected AuthApi authApi;

  @Mock
  EmailFindManager emailFindManager;

  protected MockMvc mockMvc;

  @BeforeEach()
  void beforeEach() {
    mockMvc = MockMvcBuilders.standaloneSetup(authApi).build();
  }

  @Deprecated
  @Test
  @DisplayName("Twilio 모듈을 이용해 SMS 메시지를 성공적으로 송신할 수 있다.")
  void sendSMS() throws Exception {
    // given
    String phoneNumber = "01026554276";

    // when
    ResultActions resultActions = mockMvc.perform(
      MockMvcRequestBuilders.get("/api/auth/sendSMS/" + phoneNumber)
    );

    // then
    resultActions.andExpect(status().isOk());
  }

}
