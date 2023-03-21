package com.frolic.sns.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessTokenInfo {

  private final String accessToken;

}
