package com.modular.restfulserver.post.swagger;

import com.modular.restfulserver.post.dto.CommentDetails;
import lombok.Getter;

import java.util.List;

public class CommentSchemas {

  @Getter
  public static class CommentInfoSchema {
    CommentDetails data;
  }

  @Getter
  public static class CommentListSchema {
    List<CommentDetails> data;
  }

}
