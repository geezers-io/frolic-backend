package com.frolic.sns.global.config.spring;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsTwilioConfiguration {
  @Value("${custom.twilio.sid}")
  private String sid;

  @Value("${custom.twilio.token}")
  private String token;

  @Value("{custom.twilioPhoneNumber}")
  private String twilioPhoneNumber;

  @Bean
  public void twilioConfig() {
    Twilio.init(sid, token);
  }

}
