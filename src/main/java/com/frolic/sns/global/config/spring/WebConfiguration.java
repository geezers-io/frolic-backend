package com.modular.restfulserver.global.config.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Value("${custom.path.upload-images}")
  private String uploadImagesPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    System.out.println("file:///" + uploadImagesPath + "/");
    registry
      .addResourceHandler("/static/img/**")
      .addResourceLocations("file:///" + uploadImagesPath + "/")
      .setCachePeriod(3600)
      .resourceChain(true)
      .addResolver(new PathResourceResolver());
  }
}
