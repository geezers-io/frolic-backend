package com.modular.restfulserver.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.dto.TokenInfo;
import com.modular.restfulserver.auth.dto.UserLoginRequest;
import com.modular.restfulserver.auth.dto.UserSignupRequest;
import com.modular.restfulserver.util.TestUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthSigninApiTest {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected AuthApi authApi;

  @Autowired
  protected EntityManager entityManager;

  final String username = "testuser";
  final String realname = "안드레킴";
  final String password = "@Testtest12";
  final String email = "testuser@test.com";
  UserLoginRequest userLoginRequest;
  UserSignupRequest signupRequest = UserSignupRequest.builder()
    .addUsername(username)
    .addRealname(realname)
    .addEmail(email)
    .addPassword(password)
    .addPhoneNumber(TestUser.testPhoneNumber)
    .build();

  @BeforeAll
  protected void beforeAll() throws Exception {
    mvc.perform(post("/api/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(signupRequest))
      .accept(MediaType.APPLICATION_JSON)
    ).andDo(print());
  }

  @BeforeEach
  protected void beforeEach() {
    userLoginRequest = UserLoginRequest.builder()
      .addEmail(email)
      .addPassword(password)
      .build();
  }

  @Test
  @DisplayName("정상적인 로그인이 요청이 성공적으로 수행된다.")
  void loginSuccess() throws Exception {
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.tokenInfo.accessToken").isString())
      .andExpect(jsonPath("$.data.tokenInfo.refreshToken").isString())
      .andExpect(jsonPath("$.data.userUnitedInfo.userInfo.id").isNumber())
      .andExpect(jsonPath("$.data.userUnitedInfo.userInfo.email").value(email))
      .andExpect(jsonPath("$.data.userUnitedInfo.userInfo.username").value(username))
      .andExpect(jsonPath("$.data.userUnitedInfo.userInfo.realname").value(realname))
      .andExpect(jsonPath("$.data.userUnitedInfo.userInfo.createdDate").isString())
      .andExpect(jsonPath("$.data.userUnitedInfo.userInfo.updatedDate").isString());

  }

  @Test
  @DisplayName("존재하지 않는 이메일 정보로 로그인할 시 실패한다.")
  void loginFailedEmailNotExists() throws Exception {
    userLoginRequest = UserLoginRequest.builder()
      .addEmail("anonymous@anonymous.com")
      .addPassword(password)
      .build();
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.error.message").value("요청 정보 사용자가 존재하지 않습니다."));
  }

  @Test
  @DisplayName("형식에 맞지않는 비밀번호 로그인 요청은 실패한다.")
  void loginFailedPasswordInvalid() throws Exception {
    userLoginRequest = UserLoginRequest.builder()
      .addEmail(email)
      .addPassword("@PerfectSns4275")
      .build();
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error.message").value("패스워드 정보가 일치하지 않습니다."));
  }

  @Test
  @DisplayName("잘못된 비밀번호 로그인 요청은 실패한다.")
  void loginFailedPasswordMismatch() throws Exception {
    userLoginRequest = UserLoginRequest.builder()
      .addEmail(email)
      .addPassword("@Faultpassword12")
      .build();
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error.message").value("패스워드 정보가 일치하지 않습니다."));
  }

  @Test
  @DisplayName("검증 토큰이 잘못된 액세스 토큰 갱신 요청은 실패한다.")
  void failedReissueTokenUsingInvalidAccessToken() throws Exception {
    getReissueTokenActions("setaccesstoken")
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.error").value("토큰이 유효하지 않습니다."));
  }

  @Test
  @DisplayName("정상적인 액세스 토큰 갱신 요청이 성공적으로 수행된다.")
  void reissueSuccess() throws Exception {
    TokenInfo tokenResponse = getLoginData();
    getReissueTokenActions(tokenResponse.getRefreshToken())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.accessToken").isString());
  }

  private ResultActions getLoginResultActions(UserLoginRequest userLoginRequest) throws Exception {
    return mvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(userLoginRequest))
    );
  }

  private ResultActions getReissueTokenActions(String token) throws Exception {
    return mvc.perform(get("/api/auth/reissue")
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
    )
      .andDo(print());
  }

  private TokenInfo getLoginData() {
    return Objects.requireNonNull(authApi.login(userLoginRequest).getBody()).get("data").getTokenInfo();
  }

}
