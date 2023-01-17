package com.frolic.sns.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VerifyCodeRequest {
  private String code;

  public VerifyCodeRequest(String code) {
    this.code = code;
  }

}
