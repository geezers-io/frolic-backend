package com.frolic.sns.auth.application;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@NoArgsConstructor
@Service
public class EmailFindManager {

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

}
