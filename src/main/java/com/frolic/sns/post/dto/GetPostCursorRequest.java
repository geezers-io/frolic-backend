package com.frolic.sns.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPostCursorRequest {

  private Long cursorId;

  public GetPostCursorRequest(Long cursorId) {
    this.cursorId = cursorId;
  }

}
