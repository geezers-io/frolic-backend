package com.modular.restfulserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  ALREADY_EXISTS_USER(BAD_REQUEST, "이미 존재하는 회원 정보입니다."),
  ALREADY_FOLLOW_INFO(BAD_REQUEST, "이미 팔로우된 사용자입니다."),
  PASSWORD_NOT_MATCH(BAD_REQUEST, "패스워드 정보가 일치하지 않습니다."),
  USER_NOT_FOUND(BAD_REQUEST, "존재하지 않는 유저입니다."),
  NONE_TOKEN_ON_HEADER(BAD_REQUEST, "토큰 값이 헤더에 존재하지 않습니다"),
  INVALID_TOKEN(BAD_REQUEST, "토큰 값이 유효하지 않습니다."),
  NOT_FOUND_USER(NOT_FOUND, "요청 정보 사용자가 존재하지 않습니다."),
  NOT_FOUND_RESOURCE(NOT_FOUND, "요청한 자원이 존재하지 않습니다.");

  private final HttpStatus status;
  private final String details;
}
