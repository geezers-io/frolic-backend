package com.modular.restfulserver.post.swagger;

import com.modular.restfulserver.post.dto.CommentDetail;
import lombok.Getter;

import java.util.List;

public class CommentSchemas {

  @Getter
  public static class CommentInfoSchema {
    CommentDetail data;
  }

  @Getter
  public static class CommentListSchema {
    List<CommentDetail> data;
  }

}
