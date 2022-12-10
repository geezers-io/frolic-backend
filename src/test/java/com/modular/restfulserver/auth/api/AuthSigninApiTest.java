package com.modular.restfulserver.auth.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.dto.UserLoginRequestDto;
import com.modular.restfulserver.auth.dto.UserSignupRequestDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  final String username = "testuser";
  final String realname = "안드레킴";
  final String password = "@Testtest12";
  final String email = "testuser@test.com";
  UserLoginRequestDto userLoginRequest;
  UserSignupRequestDto signupRequest = UserSignupRequestDto.builder()
    .addUsername(username)
    .addRealname(realname)
    .addEmail(email)
    .addPassword(password)
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
    userLoginRequest = UserLoginRequestDto.builder()
      .addEmail(email)
      .addPassword(password)
      .build();
  }

  @Test
  @DisplayName("정상적인 로그인이 요청이 성공적으로 수행된다.")
  void login_success() throws Exception {
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.accessToken").isString())
      .andExpect(jsonPath("$.data.refreshToken").isString())
      .andExpect(jsonPath("$.data.userInfo.userId").isNumber())
      .andExpect(jsonPath("$.data.userInfo.email").value(email))
      .andExpect(jsonPath("$.data.userInfo.username").value(username))
      .andExpect(jsonPath("$.data.userInfo.realname").value(realname))
      .andExpect(jsonPath("$.data.userInfo.createdDate").isString())
      .andExpect(jsonPath("$.data.userInfo.updatedDate").isString());

  }

  @Test
  @DisplayName("존재하지 않는 이메일 정보로 로그인할 시 실패한다.")
  void login_failed_not_exists_email() throws Exception {
    userLoginRequest = UserLoginRequestDto.builder()
      .addEmail("anonymous@anonymous.com")
      .addPassword(password)
      .build();
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.error.message").value("요청 정보 사용자가 존재하지 않습니다."));
  }

  @Test
  @DisplayName("형식에 맞지않는 비밀번호 로그인 요청은 실패한다.")
  void login_failed_password_invalid() throws Exception {
    userLoginRequest = UserLoginRequestDto.builder()
      .addEmail(email)
      .addPassword("dlrpvotmdnjemsi12")
      .build();
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error.message").value("비밀번호 형식이 잘못되었습니다."));
  }

  @Test
  @DisplayName("잘못된 비밀번호 로그인 요청은 실패한다.")
  void login_failed_password_mismatch() throws Exception {
    userLoginRequest = UserLoginRequestDto.builder()
      .addEmail(email)
      .addPassword("@Faultpassword12")
      .build();
    getLoginResultActions(userLoginRequest)
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error.message").value("패스워드 정보가 일치하지 않습니다."));
  }

  @Test
  @DisplayName("정상적인 액세스 토큰 갱신 요청이 성공적으로 수행된다.")
  void reissue_access_token_success() {

  }

  @Test
  @DisplayName("검증 토큰이 만료된 액세스 토큰 갱신 요청은 실패한다.")
  void reissue_accessToken_failed_invalid_refresh_token() {

  }

  private ResultActions getLoginResultActions(UserLoginRequestDto userLoginRequest) throws Exception {
    return mvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(userLoginRequest))
    );
  }

}
