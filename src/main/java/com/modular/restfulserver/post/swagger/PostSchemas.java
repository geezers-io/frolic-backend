package com.modular.restfulserver.post.swagger;

import com.modular.restfulserver.post.dto.PostDetails;
import lombok.Getter;

import java.util.List;

public class PostSchemas {

  @Getter
  public static class CreateArticleSchema {
    PostDetails data;
  }

  @Getter
  public static class FeedListSchema {
    List<PostDetails> data;
  }

  @Getter
  public static class LikeSchema {

    @Getter
    static class Like {
      Long count;
    }

    Like data;

  }

}
