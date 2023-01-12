package com.frolic.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestfulServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestfulServerApplication.class, args);
  }

}
