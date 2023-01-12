package com.frolic.sns.global.config.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RedisConfigurationTest {

  @Autowired
  protected RedisTemplate redisTemplate;

  @Test
  @DisplayName("redis 서버에 값을 정상적으로 삽입할 수 있다.")
  public void inputValueToRedis() {
    final ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
    stringValueOperations.set("hello", "world");
    String gotValue = stringValueOperations.get("hello");
    System.out.println("gotValue: " + gotValue);
    assertThat(gotValue).isEqualTo("world");
  }
}
