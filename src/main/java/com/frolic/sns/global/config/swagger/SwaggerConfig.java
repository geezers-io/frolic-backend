package com.frolic.sns.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
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
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components().addSecuritySchemes(
          "bearer-key",
          new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
        )
      );
  }

}
