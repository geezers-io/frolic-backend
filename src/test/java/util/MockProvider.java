package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modular.restfulserver.auth.dto.TokenDetail;
import com.modular.restfulserver.auth.dto.UserLoginResponse;
import com.modular.restfulserver.auth.dto.UserSignupRequest;
import com.modular.restfulserver.user.dto.UserDetail;
import com.modular.restfulserver.user.dto.UserUnitedDetail;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;

public class MockData {
  private ObjectMapper objectMapper = new ObjectMapper();

  public static UserDetail mockUserDetailForClientDto = UserDetail.builder()
    .addUsername("username")
    .addRealname("realname")
    .addEmail("galaxy4276@gmail.com")
    .addUpdatedDate(LocalDateTime.now())
    .addCreatedDate(LocalDateTime.now())
    .addId(1L)
    .build();

  public static TokenDetail mockTokenDetail = TokenDetail.builder()
    .addAccessToken("accessToken")
    .addRefreshToken("refreshToken")
    .build();

  public static UserUnitedDetail mockUserUnitedDetail = UserUnitedDetail.builder()
    .addUserDetail(mockUserDetailForClientDto)
    .addAllGivenLikeCount(10L)
    .addAllFollowingCount(10L)
    .addAllFollowerCount(10L)
    .addAllPostCount(120L)
    .build();

  public static UserLoginResponse mockUserLoginResponse = new UserLoginResponse(mockTokenDetail, mockUserUnitedDetail);

  public static UserSignupRequest createTestUserSignupRequest(int userNumber) {
    String name = "user" + userNumber;
    return UserSignupRequest.builder()
      .addUsername(name)
      .addRealname(name)
      .addEmail(name + "@frolic-sns.io")
      .addPassword("@Frolic" + name)
      .build();
  }

  public void signupTestUser(MockMvc mvc, UserSignupRequest userSignupRequest) throws Exception {
    mvc.perform(
      post("/api/auth/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userSignupRequest))
        .accept(MediaType.APPLICATION_JSON)
    ).andDo(print());
  }

}
