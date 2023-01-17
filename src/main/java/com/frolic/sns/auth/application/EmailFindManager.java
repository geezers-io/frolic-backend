package com.frolic.sns.auth.application;

import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.user.exception.UserNotFoundException;
import com.frolic.sns.user.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@NoArgsConstructor
@Service
public class EmailFindManager implements UserInfoFindable {

  UserRepository userRepository;

  @Deprecated
  public int randomRange(int n1, int n2) {
    return (int) (Math.random() * (n2 - n1 + 1)) + n1;
  }

  @Deprecated
  public void sendSMS(@PathVariable(name = "phoneNumber") String phoneNumber) {

    Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

    int authNum = randomRange(100000, 999999);

    String authMsg = "The authentication number is [" + authNum + "]" ;

    Message.creator(
      new PhoneNumber("+82"+phoneNumber),
      new PhoneNumber("+14807252713"),
      authMsg
    ).create();
  }

  @Override
  public String findEmail(@PathVariable(name = "phoneNumber") String phoneNumber){
      System.out.println("phoneNumber SERVICE: " + phoneNumber);

      String userEmail = userRepository.getEmailByPhoneNumber(phoneNumber)
          .orElseThrow(UserNotFoundException::new);
      System.out.println("findEmail SERVICE: " + userEmail);
      return userEmail;
  }

  @Override
  public UserFindEmailRequest findPassword(UserFindEmailRequest userFindPasswordRequest) {
    return null;
  }
}
