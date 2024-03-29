package com.frolic.sns.post.swagger;

import com.frolic.sns.post.dto.v2.PostInfo;
import lombok.Getter;

import java.util.List;

public class PostSchemas {

  @Getter
  public static class GetPostListSchema {
    List<PostInfo> data;
  }

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
