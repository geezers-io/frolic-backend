package com.modular.restfulserver.config;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class EnvironmentVariableTest {
  @Value("${server.port}")
  String port;

  @Value("${server.address}")
  String address;

  @Test
  @DisplayName("서버 주소 값을 정상적으로 가져온다.")
  void getServerAddressSuccessfully() {
    System.out.println("Address: " + address);
    assertThat(address).isInstanceOf(String.class);
  }

  @Test
  @DisplayName("서버 포트 값을 정상적으로 가져온다.")
  void getServerPortSuccessfully() {
    System.out.println("PORT: " + port);
    assertThat(port).isInstanceOf(String.class);
  }

}
