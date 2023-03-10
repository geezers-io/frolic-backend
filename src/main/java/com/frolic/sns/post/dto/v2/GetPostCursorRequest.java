package com.frolic.sns.post.dto.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class GetPostCursorRequest {

  @NotNull
  private final Long cursorId;

}
