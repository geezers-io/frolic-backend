package com.modular.restfulserver.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.api.AuthApi;
import com.modular.restfulserver.auth.dto.TokenInfo;
import com.modular.restfulserver.user.repository.UserRepository;
import com.modular.restfulserver.util.TestAuthProvider;
import com.modular.restfulserver.util.TestUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class UserManagementApiTest {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected UserManagementApi userManagementApi;

  @Autowired
  protected AuthApi authApi;

  @Autowired
  UserRepository userRepository;

  @Autowired
  TestAuthProvider testAuthProvider;

  @BeforeAll
  protected void beforeAll() {
    testAuthProvider.joinAllTestUser();
  }

  @AfterAll
  protected void afterAll() {
    testAuthProvider.clearAllTestUser();
  }

  @Transactional
  @Test
  @DisplayName("사용자 정보를 성공적으로 가져온다.")
  void successfullyGetUserUnitedDetails() throws Exception {
    getHttpGETRequestTemplate("/api/users")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.allPostCount").isNumber())
      .andExpect(jsonPath("$.data.allFollowerCount").isNumber())
      .andExpect(jsonPath("$.data.allFollowingCount").isNumber())
      .andExpect(jsonPath("$.data.allGivenLikeCount").isNumber())
      .andExpect(jsonPath("$.data.userInfo.id").isNumber())
      .andExpect(jsonPath("$.data.userInfo.email").isString())
      .andExpect(jsonPath("$.data.userInfo.username").isString())
      .andExpect(jsonPath("$.data.userInfo.realname").isString())
      .andExpect(jsonPath("$.data.userInfo.createdDate").isString())
      .andExpect(jsonPath("$.data.userInfo.updatedDate").isString());
}

  private ResultActions getHttpGETRequestTemplate(String path) throws Exception {
    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    return mvc
      .perform(
        get(path)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenInfo.getAccessToken())
      )
      .andDo(print());
  }

}
