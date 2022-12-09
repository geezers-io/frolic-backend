package com.modular.restfulserver.auth.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.dto.UserSignupRequestDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class AuthSigninApiTest {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;


  final String username = "testuser";
  final String realname = "안드레킴";
  final String email = "testuser@test.com";
  UserSignupRequestDto signupRequest = UserSignupRequestDto.builder()
    .addUsername(username)
    .addRealname(realname)
    .addEmail(email)
    .addPassword("@Testtest12")
    .build();

  @BeforeAll
  public void beforeAll() throws Exception {
    mvc.perform(post("/api/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(signupRequest))
      .accept(MediaType.APPLICATION_JSON)
    ).andDo(print());
  }

  @Test
  @DisplayName("정상적인 로그인이 요청이 성공적으로 수행된다.")
  public void login_success() {

  }

  @Test
  @DisplayName("존재하지 않는 이메일 정보로 로그인할 시 실패한다.")
  public void login_failed_not_exists_email() {

  }

  @Test
  @DisplayName("잘못된 비밀번호 로그인 요청은 실패한다.")
  public void login_failed_password_mismatch() {

  }

  @Test
  @DisplayName("정상적인 액세스 토큰 갱신 요청이 성공적으로 수행된다.")
  public void reissue_access_token_success() {

  }

  @Test
  @DisplayName("검증 토큰이 만료된 액세스 토큰 갱신 요청은 실패한다.")
  public void reissue_accessToken_failed_invalid_refresh_token() {

  }

}
