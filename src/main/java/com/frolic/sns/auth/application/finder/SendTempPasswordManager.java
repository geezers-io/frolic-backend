package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.*;
import com.frolic.sns.auth.dto.UserTempPasswordRequest;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTimeAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.global.config.spring.SmsTwilioConfiguration;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class SendTempPasswordManager extends UserInfoFindManager implements UserInfoFinderSubManageable {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final PhoneNumber sender;
  private final JavaMailSender javaMailSender;
  private static final String senderEmail = "han05081486@gmail.com";

  public SendTempPasswordManager(
          AuthCodeCacheManager authCodeCacheManager,
          UserRepository userRepository,
          PasswordEncoder passwordEncoder,
          SmsTwilioConfiguration smsTwilioConfiguration,
          JavaMailSender javaMailSender) {
    super(authCodeCacheManager);
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.sender = new PhoneNumber(smsTwilioConfiguration.getTwilioPhoneNumber());
    this.javaMailSender = javaMailSender;
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
  public String authCodeVerify(UUID id, VerifyCodeRequest request) {
    AuthCode.MetaData metaData = getAuthCode(id, FinderType.PASSWORD);

    String receiveCode = request.getCode();
    authCodeVerifyFailureCheck(metaData, id, receiveCode);

    LocalTime expiredTime = metaData.getLocalTime();
    authCodeOverTriedCheck(expiredTime);

    String receivePhoneNumber = metaData.getDestination();
    String emailValue = userRepository.getEmailByPhoneNumber(receivePhoneNumber).orElseThrow(UserNotFoundException::new);
    String userInfoExist = userRepository.getUserInfoPwExist(emailValue, receivePhoneNumber).orElseThrow(UserNotFoundException::new);

    System.out.println("service emailValue : " + emailValue);
    System.out.println("service userInfoExist : " + userInfoExist);
    System.out.println("service receivePhoneNumber : " + receivePhoneNumber);

    removeAuthCode(id);
    return userInfoExist;
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

  public String phoneNumberVal(String email){
    return userRepository.getPhoneNumberByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  @Autowired
  private EntityManager entityManager;

  //임시 비밀번호 db에 저장

  @Transactional
  public void changeTempPassword(String email, String phoneNumber, String password) {
    String encodedPassword = passwordEncoder.encode(password);

    //entityManager.persist(encodedPassword);
    userRepository.changeTempPassword(encodedPassword, email, phoneNumber);
  }


  public void sendMsgMail(String email, String password){
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(senderEmail);
    message.setTo(email);
    message.setSubject("[Frolic SNS 임시 비밀번호 발급 메일입니다.]");
    message.setText("회원님의 임시 비밀번호는 " + password + "입니다.");

    javaMailSender.send(message);
  }
}
