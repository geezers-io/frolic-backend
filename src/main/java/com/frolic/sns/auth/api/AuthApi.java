package com.frolic.sns.auth.api;

import com.frolic.sns.auth.application.AuthCodeCacheManager;
import com.frolic.sns.auth.application.AuthManager;
import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.auth.dto.UserLoginRequest;
import com.frolic.sns.auth.dto.UserLoginResponse;
import com.frolic.sns.auth.dto.UserSignupRequest;
import com.frolic.sns.auth.swagger.*;
import com.frolic.sns.global.common.ResponseHelper;
import com.frolic.sns.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    private final JwtProvider jwtProvider;
    private final AuthManager authManager;
    private final AuthCodeCacheManager authCodeCacheManager;


    @SignupDocs
    @PostMapping("/signup")
    public ResponseEntity<Map<String, UserLoginResponse>> signup(@RequestBody @Valid UserSignupRequest dto) {
        UserLoginResponse loginInfo = authManager.saveUser(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseHelper.createDataMap(loginInfo));
    }

    @LoginDocs
    @PostMapping("/login")
    public ResponseEntity<Map<String, UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest dto) {
        UserLoginResponse loginInfo = authManager.loginUser(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseHelper.createDataMap(loginInfo));
    }

    @ReissueTokenDocs
    @GetMapping("/reissue")
    public ResponseEntity<Map<String, Map<String, String>>> refresh(HttpServletRequest req) {
        String refreshToken = jwtProvider.getTokenByHttpRequestHeader(req);
        Map<String, String> tokens = authManager.refresh(refreshToken);
        return ResponseEntity.ok(ResponseHelper.createDataMap(tokens));
    }

    @LogoutDocs
    @GetMapping("/logout")
    public ResponseEntity<Void> logoutApi(HttpServletRequest request) {
        String token = jwtProvider.getTokenByHttpRequestHeader(request);
        authManager.logout(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @SendSMSDocs
    @GetMapping("/sendSMS/{phoneNumber}") // Missing
    public ResponseEntity<String> sendSMS(@PathVariable String phoneNumber) {
        System.out.println("phoneNumber: " + phoneNumber);
        ResponseEntity<String> sendSMSPhoneNumber = authCodeCacheManager.sendSMS(phoneNumber);
        return ResponseEntity.ok(ResponseHelper.createDataMap(sendSMSPhoneNumber).toString());
    }
}
