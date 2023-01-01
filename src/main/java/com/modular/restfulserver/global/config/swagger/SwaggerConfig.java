package com.modular.restfulserver.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
  info = @Info(
    title = "Frolic SNS 서비스 API 명세서",
    description = "SNS 애플리케이션 REST API 명세서",
    version = "1.0.0"
  )
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi snsOpenApi() {
    String[] paths = { "/api/**" };
    return GroupedOpenApi.builder()
      .group("SNS 서비스")
      .pathsToMatch(paths)
      .build();
  }

}
