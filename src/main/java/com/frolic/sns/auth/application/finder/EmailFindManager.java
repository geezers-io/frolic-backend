package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.global.config.spring.SmsTwilioConfiguration;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class EmailFindManager extends UserInfoFindManager implements UserInfoTransmittable {

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

  @Deprecated
  public void sendSMS(@PathVariable(name = "phoneNumber") String phoneNumber) {
    String authNum = createCode();
    String authMsg = "The authentication number is [" + authNum + "]" ;

    Message.creator(
      new PhoneNumber("+82"+phoneNumber),
      new PhoneNumber("+14807252713"),
      authMsg
    ).create();
  }

  public UUID sendCode(UserFindEmailRequest request) {
    UUID id = createId();
    String code = createCode();
    storeAuthCode(id, code, FinderType.EMAIL, request.getPhoneNumber());
    PhoneNumber receiver = new PhoneNumber(request.getPhoneNumber());
    transfer(receiver, code);
    return id;
  }

  public void verify(UUID id, VerifyCodeRequest request) {
    AuthCode.MetaData metaData = getAuthCode(id, FinderType.EMAIL);
    String receiveCode = request.getCode();
    if (!receiveCode.equals(metaData.getCode())) {
      int tryCount = metaData.getCountOfAttempts();
      if (tryCount > getMaxTryCount()) {
        removeAuthCode(id);
        throw new OverTriedAuthCodeException();
      }
      metaData.tryVerification();
      throw new MisMatchAuthCodeException();
    }
    String receivePhoneNumber = metaData.getDestination();
    String email = userRepository.getEmailByPhoneNumber(receivePhoneNumber)
      .orElseThrow(UserNotFoundException::new);
    transfer(new PhoneNumber(receivePhoneNumber), email);
  }

  @Override
  public void sendPrincipalInfo(String principalInfo, String dest) {
    transfer(new PhoneNumber(dest), principalInfo);
  }

  private void transfer(PhoneNumber receiver, String textContent) {
    Message.creator(receiver, sender, textContent).create();
  }

}
