package com.frolic.sns.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handleAlreadyUserException(CustomException exception) {
    return ErrorResponse.toResponseEntity(exception.getErrorCode());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(IllegalArgumentException ex) {
    return ErrorResponse.toResponseEntityByMessage(ex.getMessage());
  }

  @ExceptionHandler(CustomMessageException.class)
  protected ResponseEntity<Map<String, String>> handleCustomMessageException(
    CustomMessageException ex
  ) {
    return ErrorResponse.toResponseEntityByMessage(ex.getErrorMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    return ErrorResponse.toResponseEntityByArgumentNotValidException(ex);
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

  @ExceptionHandler(ResponseStatusException.class)
  protected ResponseEntity<Error> handleResponseStatusException(ResponseStatusException ex) {
    return ResponseEntity
      .status(ex.getStatus())
      .body(new Error(ex.getMessage()));
  }

}
