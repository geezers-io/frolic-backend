package com.modular.restfulserver.article.swagger;

import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import lombok.Getter;

import java.util.List;

public class ArticleSchemas {

  @Getter
  public static class CreateArticleSchema {
    SingleArticleInfoDto data;
  }

  @Getter
  public static class FeedListSchema {
    List<SingleArticleInfoDto> data;
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
