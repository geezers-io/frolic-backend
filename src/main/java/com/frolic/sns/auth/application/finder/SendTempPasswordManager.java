package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.*;
import com.frolic.sns.auth.dto.UserTempPasswordRequest;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTimeAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.global.config.spring.SmsTwilioConfiguration;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class SendTempPasswordManager extends UserInfoFindManager implements UserInfoFinderSubManageable {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final PhoneNumber sender;
  @Autowired
  private JavaMailSender javaMailSender;
  private static final String senderEmail = "han05081486@gmail.com";

  public SendTempPasswordManager(
          AuthCodeCacheManager authCodeCacheManager,
          UserRepository userRepository,
          PasswordEncoder passwordEncoder,
          SmsTwilioConfiguration smsTwilioConfiguration) {
    super(authCodeCacheManager);
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.sender = new PhoneNumber(smsTwilioConfiguration.getTwilioPhoneNumber());

  }

  public UUID sendAuthCode(UserTempPasswordRequest request) {
    UUID id = createId();
    String code = createCode();
    AuthCode authCode = AuthCode.createAuthCode(id, code, FinderType.PASSWORD,request.getPhoneNumber());
    storeAuthCode(authCode);
    send(request.getPhoneNumber(), FinderConstants.PASSWORD_AUTHCODE_SEND_MESSAGE + code);

    return id;
  }

  @Override
  public AuthCode.MetaData verifyAuthCode(UUID id, VerifyCodeRequest request) {
    AuthCode.MetaData metaData = getAuthCode(id, FinderType.PASSWORD);

    String receiveCode = request.getCode();
    authCodeVerifyFailureCheck(metaData, id, receiveCode);

    LocalTime expiredTime = metaData.getLocalTime();
    authCodeOverTriedCheck(expiredTime);

    String receivePhoneNumber = metaData.getDestination();
    String emailValue = userRepository.getEmailByPhoneNumber(receivePhoneNumber).orElseThrow(UserNotFoundException::new);
    String userInfoExist = userRepository.getUserInfoPwExist(emailValue, receivePhoneNumber).orElseThrow(UserNotFoundException::new);

    return metaData;
  }

  @Override
  public void send(String receiver, String textContent) {
    String convertedPhoneNumber = "+82" + receiver.substring(1);
    PhoneNumber receiverPhoneNumber = new PhoneNumber(convertedPhoneNumber);
    Message.creator(receiverPhoneNumber, sender, textContent).create();
  }

  private void authCodeVerifyFailureCheck(AuthCode.MetaData metaData, UUID id, String receiveCode) {
    if (!receiveCode.equals(metaData.getCode())) {
      int tryCount = metaData.getCountOfAttempts();
      if (tryCount >= FinderConstants.MAX_TRY_COUNT) {
        removeAuthCode(id);
        throw new OverTriedAuthCodeException();
      }
      metaData.tryVerification();
      storeAuthCode(AuthCode.fromMetadata(id, metaData));
      throw new MisMatchAuthCodeException();
    }
  }

  private void authCodeOverTriedCheck(LocalTime expiredTime) {
    boolean isExpired = LocalTime.now().isAfter(expiredTime);
    if (isExpired)
      throw new OverTimeAuthCodeException();
  }


  //?????? ???????????? db??? ??????

  @Transactional
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

    message.setFrom(senderEmail);
    message.setTo(email);
    message.setSubject("[Frolic SNS ?????? ???????????? ?????? ???????????????.]");
    message.setText("???????????? ?????? ??????????????? " + password + "?????????.");

    javaMailSender.send(message);
  }

}
