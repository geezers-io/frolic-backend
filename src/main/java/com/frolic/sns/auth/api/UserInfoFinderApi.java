package com.frolic.sns.auth.api;

import com.frolic.sns.auth.application.finder.EmailFindManager;
import com.frolic.sns.auth.dto.UserFindEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/finder")
public class UserInfoFinderApi {
  private final EmailFindManager emailFindManager;

  @PostMapping("/email")
  public ResponseEntity<Void> sendEmailFinderAuthCodeApi(
    @RequestBody @Valid UserFindEmailRequest request,
    HttpServletResponse response
  ) {
    UUID id = emailFindManager.sendCode(request);
    Cookie cookie = new Cookie("EMAIL_SID", String.valueOf(id));
    response.addCookie(cookie);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
