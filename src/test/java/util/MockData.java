package util;

import com.modular.restfulserver.auth.dto.TokenDetail;
import com.modular.restfulserver.auth.dto.UserLoginResponse;
import com.modular.restfulserver.user.dto.UserDetails;
import com.modular.restfulserver.user.dto.UserUnitedDetails;

import java.time.LocalDateTime;

public class MockData {
  public static UserDetails mockUserDetailsForClientDto = UserDetails.builder()
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

  public static UserUnitedDetails mockUserUnitedDetails = UserUnitedDetails.builder()
    .addUserDetails(mockUserDetailsForClientDto)
    .addAllGivenLikeCount(10L)
    .addAllFollowingCount(10L)
    .addAllFollowerCount(10L)
    .addAllPostCount(120L)
    .build();

  public static UserLoginResponse mockUserLoginResponse = new UserLoginResponse(mockTokenDetail, mockUserUnitedDetails);

}
