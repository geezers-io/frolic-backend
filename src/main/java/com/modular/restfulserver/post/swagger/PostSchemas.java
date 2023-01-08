package com.modular.restfulserver.post.swagger;

import com.modular.restfulserver.post.dto.PostInfo;
import lombok.Getter;

import java.util.List;

public class PostSchemas {

  @Getter
  public static class CreateArticleSchema {
    PostInfo data;
  }

  @Getter
  public static class FeedListSchema {
    List<PostInfo> data;
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
