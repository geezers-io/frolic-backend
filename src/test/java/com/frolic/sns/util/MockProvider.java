package com.frolic.sns.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frolic.sns.auth.dto.TokenInfo;
import com.frolic.sns.auth.dto.UserLoginRequest;
import com.frolic.sns.auth.dto.UserLoginResponse;
import com.frolic.sns.auth.dto.UserSignupRequest;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.dto.UserUnitedInfo;
import com.frolic.sns.user.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

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

  public static UserSignupRequest createTestUserSignupRequest(TestUser testUser) {
    User user = testUser.getUser();
    return UserSignupRequest.builder()
      .addUsername(user.getUsername())
      .addRealname(user.getRealname())
      .addPhoneNumber(TestUser.testPhoneNumber)
      .addEmail(user.getEmail())
      .addPassword(TestUser.testPassword)
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
