package com.modular.restfulserver.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.dto.UserSignupRequestDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthSignupApiTest {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  UserSignupRequestDto signupRequest;
  final String username = "testuser";
  final String realname = "안드레킴";
  final String email = "testuser@test.com";

  @BeforeEach
  public void beforeEach() {
    signupRequest = UserSignupRequestDto.builder()
      .addUsername(username)
      .addRealname(realname)
      .addEmail(email)
      .addPassword("@Testtest12")
      .build();
  }

//  @Test
//  @DisplayName("사용자가 정상적으로 생성된다.")
//  public void createUser() throws Exception {
//    // given
//        // signupRequest
//
//    // when
//    ResultActions resultActions = getSignupResultActions(signupRequest);
//
//    // then
//    resultActions
//      .andExpect(status().isCreated())
//      .andExpect(jsonPath("$.data.accessToken").isString())
//      .andExpect(jsonPath("$.data.refreshToken").isString())
//      .andExpect(jsonPath("$.data.userInfo.userId").isNumber())
//      .andExpect(jsonPath("$.data.userInfo.email").value(email))
//      .andExpect(jsonPath("$.data.userInfo.username").value(username))
//      .andExpect(jsonPath("$.data.userInfo.realname").value(realname))
//      .andExpect(jsonPath("$.data.userInfo.createdDate").isString())
//      .andExpect(jsonPath("$.data.userInfo.updatedDate").isString());
//
//  }

  @Test
  @DisplayName("username 이 형식에 맞지 않은 회원가입 요청은 실패한다.")
  public void createUser_failed_username() throws Exception {
    // given
    signupRequest.setUsername("엄준식이가");
    String error = "사용자 이름 형식이 잘못되었습니다.";

    // when
    ResultActions resultActions = getSignupResultActions(signupRequest);

    // then
    validateBadRequestError(resultActions, error);
  }

  @Test
  @DisplayName("realname 이 형식에 맞지 않은 회원가입 요청은 실패한다.")
  public void createUser_failed_realname() throws Exception {
    // given
    signupRequest.setRealname("isReal이즈real12");
    String error = "실명 형식이 잘못되었습니다.";

    // when
    ResultActions resultActions = getSignupResultActions(signupRequest);

    // then
    validateBadRequestError(resultActions, error);
  }

  @Test
  @DisplayName("password 가 형식에 맞지 않은 회원가입 요청은 실패한다.")
  public void createUser_failed_password() throws Exception {
    // given
    signupRequest.setPassword("tlrPthfl12");
    String error = "비밀번호 형식이 잘못되었습니다.";

    // when
    ResultActions resultActions = getSignupResultActions(signupRequest);

    // then
    validateBadRequestError(resultActions, error);
  }

  @Test
  @DisplayName("이미 회원가입한 사용자 이메일 정보로 회원가입 요청은 실패한다.")
  public void createUser_exists_email() throws Exception {
    // given
    getSignupResultActions(signupRequest);
    signupRequest.setUsername("another12");
    signupRequest.setRealname("another");

    // when
    ResultActions resultActions = getSignupResultActions(signupRequest);

    // then
    validateBadRequestError(resultActions, "이미 존재하는 회원 정보입니다.");
  }

  @Test
  @DisplayName("이미 회원가입한 사용자 이름(username) 정보로 회원가입 요청은 실패한다.")
  public void createUser_exists_username() throws Exception {
    // given
    getSignupResultActions(signupRequest);
    signupRequest.setRealname("another");
    signupRequest.setEmail("another@world.com");

    // when
    ResultActions resultActions = getSignupResultActions(signupRequest);

    // then
    validateBadRequestError(resultActions, "이미 존재하는 회원 정보입니다.");
  }

  private ResultActions getSignupResultActions(Object contentValue) throws Exception {
    return mvc.perform(post("/api/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(contentValue))
      .accept(MediaType.APPLICATION_JSON)
    ).andDo(print());
  }

  private void validateBadRequestError(ResultActions actions, String error) throws Exception {
    actions
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.error.message").value(error));
  }

}
