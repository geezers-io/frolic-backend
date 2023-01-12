package com.frolic.sns.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frolic.sns.auth.api.AuthApi;
import com.frolic.sns.auth.dto.TokenInfo;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import com.frolic.sns.util.MockProvider;
import com.frolic.sns.util.TestAuthProvider;
import com.frolic.sns.util.TestUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class GetUserInfoApiTest {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected UserManagementApi userManagementApi;

  @Autowired
  protected AuthApi authApi;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected TestAuthProvider testAuthProvider;

  @BeforeAll
  protected void beforeAll() {
    testAuthProvider.joinAllTestUser();
  }

  @AfterAll
  protected void afterAll() {
    testAuthProvider.clearAllTestUser();
  }

  @Test
  @DisplayName("사용자 정보를 성공적으로 가져온다.")
  void getUserUnitedInfoSuccess() throws Exception {
    MockHttpServletRequestBuilder request = get("/api/users");
    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    MockProvider.setHeaderWithToken(request, tokenInfo.getAccessToken());
    mvc.perform(request)
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

  @Test
  @DisplayName("로그인하지 않은 유저의 /api/users 요청은 403 을 반환한다.")
  void noTokenIs403() throws Exception {
    mvc.perform(get("/api/users"))
      .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("사용자 정보를 성공적으로 가져온다.")
  void getUserUnitedInfoByUsernameIsSuccess() throws Exception {
    User user = TestUser.JUNEJAE.getUser();
    MockHttpServletRequestBuilder request = get("/api/users/" + user.getUsername());
    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    MockProvider.setHeaderWithToken(request, tokenInfo.getAccessToken());
    mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.userInfo.id").isNumber());
  }

  @Test
  @DisplayName("존재하지 않은 유저의 정보 요청은 404를 반환한다.")
  void getNotExistsUser() throws Exception {
    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    MockHttpServletRequestBuilder request = get("/api/users/anonymous");
    MockProvider.setHeaderWithToken(request, tokenInfo.getAccessToken());
    mvc.perform(request).andExpect(status().isNotFound());
  }

}
