package com.frolic.sns.post.dto;

import com.frolic.sns.post.dto.v2.PostInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostListResponse {

  private final List<PostInfo> posts;

  private final Long cursorId;

}
