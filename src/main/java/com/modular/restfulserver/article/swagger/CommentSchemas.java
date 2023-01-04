package com.modular.restfulserver.article.swagger;

import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import lombok.Getter;

import java.util.List;

public class CommentSchemas {

  @Getter
  public static class CommentInfoSchema {
    SingleCommentInfoDto data;
  }

  @Getter
  public static class CommentListSchema {
    List<SingleCommentInfoDto> data;
  }

}
