package com.frolic.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = { MailSenderAutoConfiguration.class })
@EnableJpaAuditing
public class RestfulServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestfulServerApplication.class, args);
  }

}
