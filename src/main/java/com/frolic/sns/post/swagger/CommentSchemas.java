package com.frolic.sns.post.swagger;

import com.frolic.sns.post.dto.CommentInfo;
import lombok.Getter;

import java.util.List;

public class CommentSchemas {

  @Getter
  public static class CommentInfoSchema {
    CommentInfo data;
  }

  @Getter
  public static class CommentListSchema {
    List<CommentInfo> data;
  }

}
