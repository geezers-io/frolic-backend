package com.modular.restfulserver.global.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfiguration {

  private final JwtFilter jwtFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final CustomAuthProvider customAuthProvider;

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /* off security features **/
    http
      .csrf().disable()
      .cors().configurationSource(corsConfigurationSource())
      .and()
      .formLogin().disable()
      .sessionManagement(
        session -> session.sessionCreationPolicy(
          SessionCreationPolicy.STATELESS
        )
      );

    /* config **/
    http
      .headers().frameOptions().sameOrigin().and()
      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .and()
      .authorizeRequests()
      .antMatchers(
        "/api/auth/login",
        "/api/auth/signup",
        "/api/users/{username}",
        "/api/posts/list",
        "/api/download/{filename}",
        "/api/posts/{postId}",
        "/api/posts/search",
        "/api/comments/{commentId}",
        "/api/comments/posts/{postId}",
        "/api/comments/username/{username}"
        ).permitAll()
      .anyRequest().authenticated()
      .and()
      .httpBasic()
      .and()
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    String idForEncode = "bcrypt";
    Map<String, PasswordEncoder> encoderMap = new HashMap<>();
    encoderMap.put(idForEncode, new BCryptPasswordEncoder());

    return new DelegatingPasswordEncoder(idForEncode, encoderMap);
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
      http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(customAuthProvider);
    return authenticationManagerBuilder.build();
  }

}
