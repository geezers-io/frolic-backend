package com.modular.restfulserver.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handleAlreadyUserException(
    CustomException exception
  ) {
    return ErrorResponse.toResponseEntity(exception.getErrorCode());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(IllegalArgumentException ex) {
    log.error("여기안탐");
    return ErrorResponse.toResponseEntityByMessage(ex.getMessage());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  protected ResponseEntity<Map<String, String>> handleUsernameNotFound(
    UsernameNotFoundException ex
  ) {
    Map<String, String> response = new HashMap<>();
    response.put("error", ex.getMessage());
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(response);
  }

}
