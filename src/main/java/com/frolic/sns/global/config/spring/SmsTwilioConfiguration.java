package com.frolic.sns.global.config.spring;

import com.twilio.Twilio;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Slf4j
@Configuration
public class SmsTwilioConfiguration {
  private final String sid;

  private final String token;


  private final String twilioPhoneNumber;

  @Autowired
  public SmsTwilioConfiguration(
    @Value("${custom.twilio.token}") final String token,
    @Value("${custom.twilio.phone-number}") final String twilioPhoneNumber,
    @Value("${custom.twilio.sid}") final String sid
  ) {
    this.token = token;
    this.sid = sid;
    this.twilioPhoneNumber = twilioPhoneNumber;
    Twilio.setAccountSid(sid);
    Twilio.setUsername(sid);
    Twilio.setPassword(token);
    log.info("Initializing Twilio Configuration");
  }

}
