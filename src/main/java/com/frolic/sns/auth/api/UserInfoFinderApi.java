package com.frolic.sns.auth.api;

import com.frolic.sns.auth.application.finder.EmailFindManager;
import com.frolic.sns.auth.application.finder.SendTempPasswordManager;
import com.frolic.sns.auth.application.finder.TempPasswordCreator;
import com.frolic.sns.auth.application.finder.common.AuthCode;
import com.frolic.sns.auth.application.finder.common.AuthCodeCacheManager;
import com.frolic.sns.auth.application.finder.common.FinderType;
import com.frolic.sns.auth.application.finder.common.UserInfoFindManager;
import com.frolic.sns.auth.dto.*;
import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.exception.NotFoundCookieException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/finder")
public class UserInfoFinderApi {
  private final EmailFindManager emailFindManager;
  private final SendTempPasswordManager sendTempPasswordManager;
  private final TempPasswordCreator tempPasswordCreator;
  private final UserRepository userRepository;

  private final UserInfoFindManager userInfoFindManager;

  private final PasswordEncoder passwordEncoder;

  private final AuthCodeCacheManager authCodeCacheManager;

  @PostMapping("/email")
  public ResponseEntity<Void> sendEmailFinderAuthCodeApi(
          @RequestBody @Valid UserFindEmailRequest request,
          HttpServletResponse response
  ) {
    UUID id = emailFindManager.sendAuthCode(request);

    setSidCookie(response, SidType.EMAIL_SID, id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Cacheable(value = "password", key = "#request.email")
  @PostMapping("/password")
  public ResponseEntity<Void> sendTempPasswordAuthCodeApi(
          @RequestBody @Valid UserTempPasswordRequest request,
          HttpServletResponse response
  ) {
    UUID id = sendTempPasswordManager.sendAuthCode(request);
    setSidCookie(response, SidType.TEMP_PASSWORD_SID, id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/email/check")
  public ResponseEntity<Map<String, UserFindEmailResponse>> verifyEmailAuthCodeApi(
    @RequestBody @Valid VerifyCodeRequest verifyCodeRequest,
    HttpServletRequest httpRequest
  ) {
    Cookie[] cookies = httpRequest.getCookies();
    UUID id = parseSidFromCookies(cookies, SidType.EMAIL_SID);
    String email = emailFindManager.authCodeVerify(id, verifyCodeRequest);
    UserFindEmailResponse response = new UserFindEmailResponse(email);
    return ResponseEntity.ok(ResponseHelper.createDataMap(response));
  }

  @PostMapping("/password/check")
  public ResponseEntity<Map<String, UserTempPasswordResponse>> verifyTempPasswordAuthCodeApi(
          @RequestBody @Valid VerifyCodeRequest verifyCodeRequest,
          HttpServletRequest httpRequest
  ) {
    Cookie[] cookies = httpRequest.getCookies();
    UUID id = parseSidFromCookies(cookies, SidType.TEMP_PASSWORD_SID);

    String email = sendTempPasswordManager.authCodeVerify(id, verifyCodeRequest);

    //String testhp = userInfoFindManager.getAuthCode(id, FinderType.PASSWORD).getDestination();
    AuthCode.MetaData metaData_1 = userInfoFindManager.getAuthCode(id, FinderType.PASSWORD);
    String testhp = metaData_1.getDestination();

    System.out.println("testhp : "+ testhp);
    System.out.println("controller phoneNumber : " + testhp);
    String password = tempPasswordCreator.create();
    System.out.println("controller password : " + password);

    String encodedPassword = passwordEncoder.encode(password);

    userRepository.save(User.builder().addEmail(email).addPhoneNumber(testhp).addPassword(encodedPassword).build());
    sendTempPasswordManager.sendMsgMail(email, password);
    
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  /**
   * @implNote 요청한 클라이언트에 지정한 세션 쿠키 값을 설정합니다.
   */
  private void setSidCookie(HttpServletResponse response, SidType sidType, UUID emailSid) {
    Cookie cookie = new Cookie(sidType.name(), emailSid.toString());
    response.addCookie(cookie);
  }

  /**
   * @implNote 요청한 클라이언트의 쿠키 값 배열에서 지정한 세션 쿠키의 값을 가져옵니다. 없으면 오류
   */
  private UUID parseSidFromCookies(Cookie[] cookies, SidType sidType) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(sidType.name()))
        return UUID.fromString(cookie.getValue());
    }
    throw new NotFoundCookieException();
  }

}
