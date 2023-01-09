package com.modular.restfulserver.auth.application;

import com.modular.restfulserver.auth.dto.TokenDetails;
import com.modular.restfulserver.auth.dto.UserLoginRequest;
import com.modular.restfulserver.auth.dto.UserLoginResponse;
import com.modular.restfulserver.auth.dto.UserSignupRequest;
import com.modular.restfulserver.auth.exception.AlreadyExistsUserException;
import com.modular.restfulserver.auth.exception.InvalidTokenException;
import com.modular.restfulserver.auth.exception.PasswordNotMatchException;
import com.modular.restfulserver.global.config.security.CustomEmailPasswordAuthToken;
import com.modular.restfulserver.global.config.security.JwtConstants;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.post.repository.LikeRepository;
import com.modular.restfulserver.post.repository.PostRepository;
import com.modular.restfulserver.user.dto.UserDetails;
import com.modular.restfulserver.user.dto.UserUnitedDetails;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.FollowRepository;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthManager implements AuthManageable {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final FollowRepository followRepository;
  private final JwtProvider jwtProvider;
  private final LikeRepository likeRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public UserLoginResponse saveUser(UserSignupRequest userSignupRequest) {
    boolean isExistsEmail = userRepository.existsByEmail(userSignupRequest.getEmail());
    boolean isExistsUsername = userRepository.existsByUsername(userSignupRequest.getUsername());
    if (isExistsEmail || isExistsUsername)
      throw new AlreadyExistsUserException();

    String rawPassword = userSignupRequest.getPassword();
    String encodedPassword = passwordEncoder.encode(rawPassword);
    userSignupRequest.setEncodePassword(encodedPassword);
    userRepository.save(userSignupRequest.toEntity());

    UserLoginRequest userLoginRequest = UserLoginRequest.builder()
      .addEmail(userSignupRequest.getEmail())
      .addPassword(rawPassword)
      .build();

    return this.loginUser(userLoginRequest);
  }

  public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
    User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
      throw new PasswordNotMatchException();

    CustomEmailPasswordAuthToken emailPasswordAuthToken = new CustomEmailPasswordAuthToken(userLoginRequest.getEmail());
    Authentication authentication = authenticationManager.authenticate(emailPasswordAuthToken);

    String email = authentication.getName();

    String accessToken = jwtProvider.createAccessToken(email);
    String refreshToken = jwtProvider.createRefreshToken(email);
    user.updateRefreshToken(refreshToken);

    TokenDetails tokenDetails = TokenDetails.builder()
      .addAccessToken(accessToken)
      .addRefreshToken(refreshToken)
      .build();
    Long allPostCount = postRepository.countAllByUser(user);
    Long allFollowerCount = followRepository.countByFollowerId(user);
    Long allFollowingCount = followRepository.countByFollowingId(user);
    Long allGivenLikeCount = likeRepository.countAllByUser(user);

    UserDetails userDetails = UserDetails.from(user);
    UserUnitedDetails userUnitedDetails = UserUnitedDetails.builder()
      .addUserDetails(userDetails)
      .addAllFollowerCount(allFollowerCount)
      .addAllFollowingCount(allFollowingCount)
      .addAllGivenLikeCount(allGivenLikeCount)
      .addAllPostCount(allPostCount)
      .build();

    return UserLoginResponse.create(tokenDetails, userUnitedDetails);
  }

  public Map<String, String> refresh(String refreshToken) {
    String email = jwtProvider.getUserEmailByToken(refreshToken);
    Map<String, String> response = new HashMap<>();
    String newAccessToken = jwtProvider.createAccessToken(email);
    String userRegisteredToken = userRepository.getUserRefreshToken(email);

    if (!refreshToken.equals(userRegisteredToken))
      throw new InvalidTokenException();
    response.put(JwtConstants.ACCESS_TOKEN, newAccessToken);

    return response;
  }

  public void logout(String token) {
    String email = jwtProvider.getUserEmailByToken(token);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.updateRefreshToken(null);
  }

}
