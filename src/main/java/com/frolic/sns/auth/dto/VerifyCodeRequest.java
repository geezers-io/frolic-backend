package com.frolic.sns.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class VerifyCodeRequest {

  @NotNull
  private String code;

  public VerifyCodeRequest(String code) {
    this.code = code;
  }

}
