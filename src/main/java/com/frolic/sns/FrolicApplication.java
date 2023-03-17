package com.frolic.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = { MailSenderAutoConfiguration.class })
@EnableJpaAuditing
public class FrolicApplication {

  public static void main(String[] args) {
    SpringApplication.run(FrolicApplication.class, args);
  }

}
