package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.*;
import com.frolic.sns.auth.config.EmailManagerProperties;
import com.frolic.sns.auth.dto.UserTempPasswordRequest;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTimeAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.global.config.spring.SmsTwilioConfiguration;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendTempPasswordManager implements UserFinderManageable {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JavaMailSender javaMailSender;
  private final SmsTwilioConfiguration smsTwilioConfiguration;
  private final UserFinderManager userFinderManager;
  private final EmailManagerProperties emailManagerProperties;

  public UUID sendAuthCode(UserTempPasswordRequest request) {
    UUID id = userFinderManager.createId();
    String code = userFinderManager.createCode();
    AuthCode authCode = AuthCode.createAuthCode(id, code, FinderType.PASSWORD,request.getPhoneNumber());
    userFinderManager.storeAuthCode(authCode);
    send(request.getPhoneNumber(), FinderConstants.PASSWORD_AUTHCODE_SEND_MESSAGE + code);

    return id;
  }

  @Override
  public AuthCode.MetaData verifyAuthCode(UUID id, VerifyCodeRequest request) {
    AuthCode.MetaData metaData = userFinderManager.getAuthCode(id, FinderType.PASSWORD);

    String receiveCode = request.getCode();
    authCodeVerifyFailureCheck(metaData, id, receiveCode);

    LocalTime expiredTime = metaData.getLocalTime();
    authCodeOverTriedCheck(expiredTime);
    return metaData;
  }

  @Override
  public void send(String receiver, String textContent) {
    String convertedPhoneNumber = "+82" + receiver.substring(1);
    PhoneNumber receiverPhoneNumber = new PhoneNumber(convertedPhoneNumber);
    Message.creator(receiverPhoneNumber, smsTwilioConfiguration.getSenderPhoneNumber(), textContent).create();
  }

  private void authCodeVerifyFailureCheck(AuthCode.MetaData metaData, UUID id, String receiveCode) {
    if (!receiveCode.equals(metaData.getCode())) {
      int tryCount = metaData.getCountOfAttempts();
      if (tryCount >= FinderConstants.MAX_TRY_COUNT) {
        userFinderManager.removeAuthCode(id);
        throw new OverTriedAuthCodeException();
      }
      metaData.tryVerification();
      userFinderManager.storeAuthCode(AuthCode.fromMetadata(id, metaData));
      throw new MisMatchAuthCodeException();
    }
  }

  //임시 비밀번호 db에 저장

  public void changeTempPassword(String password, String phoneNumber) {
    String encodedPassword = passwordEncoder.encode(password);
    String email = userRepository.getEmailByPhoneNumber(phoneNumber).orElseThrow();

    User user = userRepository.findByEmail(email).orElseThrow();
    user.changePassword(encodedPassword);

    userRepository.save(user);
  }

  public void sendMsgMail(String password, String phoneNumber){
    SimpleMailMessage message = new SimpleMailMessage();
    String email = userRepository.getEmailByPhoneNumber(phoneNumber).orElseThrow();

    message.setFrom(emailManagerProperties.getUsername());
    message.setTo(email);
    message.setSubject("[Frolic SNS 임시 비밀번호 발급 메일입니다.]");
    message.setText("회원님의 임시 비밀번호는 " + password + "입니다.");

    javaMailSender.send(message);
  }

  private void authCodeOverTriedCheck(LocalTime expiredTime) {
    boolean isExpired = LocalTime.now().isAfter(expiredTime);
    if (isExpired)
      throw new OverTimeAuthCodeException();
  }

}
