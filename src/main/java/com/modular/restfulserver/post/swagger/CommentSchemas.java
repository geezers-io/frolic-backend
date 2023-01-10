package com.modular.restfulserver.post.swagger;

import com.modular.restfulserver.post.dto.CommentInfo;
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
