package com.modular.restfulserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.dto.TokenInfo;
import com.modular.restfulserver.auth.dto.UserLoginRequest;
import com.modular.restfulserver.auth.dto.UserLoginResponse;
import com.modular.restfulserver.auth.dto.UserSignupRequest;
import com.modular.restfulserver.user.dto.UserInfo;
import com.modular.restfulserver.user.dto.UserUnitedInfo;
import com.modular.restfulserver.user.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockProvider {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static UserInfo mockUserInfoForClientDto = UserInfo.builder()
    .addUsername("username")
    .addRealname("realname")
    .addPhoneNumber(TestUser.testPhoneNumber)
    .addEmail("galaxy4276@gmail.com")
    .addUpdatedDate(LocalDateTime.now())
    .addCreatedDate(LocalDateTime.now())
    .addId(1L)
    .build();

  public static TokenInfo mockTokenInfo = TokenInfo.builder()
    .addAccessToken("accessToken")
    .addRefreshToken("refreshToken")
    .build();

  public static UserUnitedInfo mockUserUnitedInfo = UserUnitedInfo.builder()
    .addUserInfo(mockUserInfoForClientDto)
    .addAllGivenLikeCount(10L)
    .addAllFollowingCount(10L)
    .addAllFollowerCount(10L)
    .addAllPostCount(120L)
    .build();

  public static UserLoginResponse mockUserLoginResponse = new UserLoginResponse(mockTokenInfo, mockUserUnitedInfo);

  public static UserSignupRequest createTestUserSignupRequest(int userNumber) {
    String name = "user" + userNumber;
    return UserSignupRequest.builder()
      .addUsername(name)
      .addRealname("realname")
      .addPhoneNumber(TestUser.testPhoneNumber)
      .addEmail(name + "@frolic-sns.io")
      .addPassword("@Frolic" + name)
      .build();
  }

  public static UserLoginRequest createTestUserLoginRequest(TestUser testUser) {
    User user = testUser.getUser();
    return UserLoginRequest.builder()
      .addEmail(user.getEmail())
      .addPassword(TestUser.testPassword)
      .build();
  }

  public static void signupTestUser(MockMvc mvc, UserSignupRequest userSignupRequest) throws Exception {
    mvc.perform(
      post("/api/auth/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userSignupRequest))
        .accept(MediaType.APPLICATION_JSON)
    ).andDo(print());
  }

  public static void setHeaderWithToken(MockHttpServletRequestBuilder mock, String accessToken) {
    mock.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
  }

}
