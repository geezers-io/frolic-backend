package com.modular.restfulserver.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handleAlreadyUserException(
    CustomException exception
  ) {
    return ErrorResponse.toResponseEntity(exception.getErrorCode());
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
