package com.modular.restfulserver.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.api.AuthApi;
import com.modular.restfulserver.auth.dto.TokenInfo;
import com.modular.restfulserver.auth.exception.PasswordNotMatchException;
import com.modular.restfulserver.user.application.UserManager;
import com.modular.restfulserver.user.dto.PasswordUpdateRequest;
import com.modular.restfulserver.user.dto.UserUpdateRequest;
import com.modular.restfulserver.user.repository.UserRepository;
import com.modular.restfulserver.util.MockProvider;
import com.modular.restfulserver.util.TestAuthProvider;
import com.modular.restfulserver.util.TestUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class UpdateUserInfoApiTest {

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

  @Autowired
  protected UserManager userManager;

  @BeforeAll
  protected void beforeAll() {
    testAuthProvider.joinAllTestUser();
  }

  @AfterAll
  protected void afterAll() {
    testAuthProvider.clearAllTestUser();
  }

  @Test
  @DisplayName("사용자 정보를 성공적으로 수정한다.")
  void updateUserInfoSuccess() throws Exception {
    UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
      .addEmail("update64@gmail.com")
      .addRealname("정지용")
      .addUsername("jedily123")
      .addPhoneNumber("010-3343-5456")
      .build();

    MockHttpServletRequestBuilder request = put("/api/users")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(userUpdateRequest));

    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    MockProvider.setHeaderWithToken(request, tokenInfo.getAccessToken());
    mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인하지 않은 유저의 수정 요청은 403 을 반환한다.")
  void noTokenIs403() throws Exception {
    mvc.perform(put("/api/users")).andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("유저 패스워드 변경이 성공적으로 수행된다.")
  void changeUserPasswordSuccess() {
    PasswordUpdateRequest updateRequest = new PasswordUpdateRequest(TestUser.testPassword, "@Perfectsns4275");
    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    assertDoesNotThrow(() -> userManager.updateUserPassword(tokenInfo.getAccessToken(), updateRequest));
  }

  @Test
  @DisplayName("잘못된 패스워드 변경의 요청은 실패한다.")
  void changeUserPasswordFaultInfo() {
    PasswordUpdateRequest updateRequest = new PasswordUpdateRequest("@Faultpassword12", "@Newpassword12");
    TokenInfo tokenInfo = testAuthProvider.getTokenInfo(TestUser.EUNGI);
    assertThrows(PasswordNotMatchException.class, () -> {
      userManager.updateUserPassword(tokenInfo.getAccessToken(), updateRequest);
    });
  }

}
