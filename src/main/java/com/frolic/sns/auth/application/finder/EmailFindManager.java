package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.*;
import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.auth.dto.UserFindEmailResponse;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTimeAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.global.config.spring.SmsTwilioConfiguration;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailFindManager implements UserFinderManageable {

  private final UserRepository userRepository;
  private final SmsTwilioConfiguration smsTwilioConfiguration;
  private final UserFinderManager userFinderManager;

  public UUID sendAuthCode(UserFindEmailRequest request) {
    userRepository.getEmailByPhoneNumber(request.getPhoneNumber()).orElseThrow(UserNotFoundException::new);
    UUID id = userFinderManager.createId();
    String code = userFinderManager.createCode();
    AuthCode authCode = AuthCode.createAuthCode(id, code, FinderType.EMAIL, request.getPhoneNumber());
    userFinderManager.storeAuthCode(authCode);
    send(request.getPhoneNumber(), FinderConstants.EMAIL_AUTHCODE_SEND_MESSAGE + code);
    return id;
  }

  @Override
  public AuthCode.MetaData verifyAuthCode(UUID id, VerifyCodeRequest request) {
    AuthCode.MetaData metaData = userFinderManager.getAuthCode(id, FinderType.EMAIL);

    String receiveCode = request.getCode();
    verifyAuthcodeFailureCheck(metaData, id, receiveCode);

    LocalTime expiredTime = metaData.getLocalTime();
    checkOverTriedAuthcode(expiredTime);

    String receivePhoneNumber = metaData.getDestination();
    String email = userRepository.getEmailByPhoneNumber(receivePhoneNumber).orElseThrow(UserNotFoundException::new);
    userFinderManager.removeAuthCode(id);
    return metaData;
  }

  @Override
  public void send(String receiver, String textContent) {
    String convertedPhoneNumber = "+82" + receiver.substring(1);
    PhoneNumber receiverPhoneNumber = new PhoneNumber(convertedPhoneNumber);
    Message.creator(receiverPhoneNumber, smsTwilioConfiguration.getSenderPhoneNumber(), textContent).create();
  }

  public UserFindEmailResponse getFindEmailByDest(String dest) {
    String email = userRepository.getEmailByPhoneNumber(dest).orElseThrow(UserNotFoundException::new);
    return  new UserFindEmailResponse(email);
  }

  private void verifyAuthcodeFailureCheck(AuthCode.MetaData metaData, UUID id, String receiveCode) {
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

  private void checkOverTriedAuthcode(LocalTime expiredTime) {
    boolean isExpired = LocalTime.now().isAfter(expiredTime);
    if (isExpired)
      throw new OverTimeAuthCodeException();
  }

}
