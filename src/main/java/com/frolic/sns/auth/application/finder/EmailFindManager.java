package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.*;
import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTimeAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.global.config.spring.SmsTwilioConfiguration;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class EmailFindManager extends UserInfoFindManager implements UserInfoFinderSubManageable {

  private final UserRepository userRepository;
  private final PhoneNumber sender;

  public EmailFindManager(
    AuthCodeCacheManager authCodeCacheManager,
    UserRepository userRepository,
    SmsTwilioConfiguration smsTwilioConfiguration
  ) {
    super(authCodeCacheManager);
    this.userRepository = userRepository;
    this.sender = new PhoneNumber(smsTwilioConfiguration.getTwilioPhoneNumber());
  }

  public UUID sendAuthCode(UserFindEmailRequest request) {
    userRepository.getEmailByPhoneNumber(request.getPhoneNumber()).orElseThrow(UserNotFoundException::new);
    UUID id = createId();
    String code = createCode();
    AuthCode authCode = AuthCode.createAuthCode(id, code, FinderType.EMAIL, request.getPhoneNumber());
    storeAuthCode(authCode);
    send(request.getPhoneNumber(), FinderConstants.EMAIL_AUTHCODE_SEND_MESSAGE + code);
    return id;
  }

  @Override
  public String authCodeVerify(UUID id, VerifyCodeRequest request) {
    AuthCode.MetaData metaData = getAuthCode(id, FinderType.EMAIL);

    String receiveCode = request.getCode();
    authCodeVerifyFailureCheck(metaData, id, receiveCode);

    LocalTime expiredTime = metaData.getLocalTime();
    authCodeOverTriedCheck(expiredTime);

    String receivePhoneNumber = metaData.getDestination();
    String email = userRepository.getEmailByPhoneNumber(receivePhoneNumber).orElseThrow(UserNotFoundException::new);
    removeAuthCode(id);
    return email;
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

}
