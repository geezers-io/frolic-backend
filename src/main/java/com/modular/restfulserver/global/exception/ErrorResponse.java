package com.modular.restfulserver.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder(setterPrefix = "add")
public class ErrorResponse {

  private final Map<String, String> error;

  public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode code) {
    var responseMap = new HashMap<String, String>();
    responseMap.put("message", code.getDetails());

    return ResponseEntity
      .status(code.getStatus())
      .body(
        ErrorResponse.builder()
          .addError(responseMap)
          .build()
      );
  }

  public static ResponseEntity<Map<String, String>> toResponseEntityByMessage(String message) {
    var responseMap = new HashMap<String, String>();
    responseMap.put("error", message);

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(responseMap);
  }

  public static ResponseEntity<Object> toResponseEntityByArgumentNotValidException(
    MethodArgumentNotValidException ex
  ) {
    var responseMap = new HashMap<String, String>();
    String fieldErrorList = ex.getFieldErrors()
        .stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList())
          .get(0); // 다중 에러는 항상 첫번 째 것만 반환한다.
    responseMap.put("message", fieldErrorList);

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(
        ErrorResponse.builder()
          .addError(responseMap)
          .build()
      );
  }

}
