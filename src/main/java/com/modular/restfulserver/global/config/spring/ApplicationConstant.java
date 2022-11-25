package com.modular.restfulserver.global.config.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApplicationConstant {

  @Value("${host}")
  public static String HOST = "http://localhost";

  @Value("${port}")
  public static String PORT = "8080";

 public ApplicationConstant() {
   // TODO: 2022-11-25 외안호디ㅗㅠㅠㅠ/
   log.warn("[ApplicationConstant] application.properties 값으로 추 후 초기화 진행해주세요.");
 }
  
}
