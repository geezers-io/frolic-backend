package com.modular.restfulserver.article.dto;

import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SingleArticleInfoDto {

  private final Article postInfo;
  private final List<Comment> comments;

  @Builder(setterPrefix = "add")
  public SingleArticleInfoDto(
    Article postInfo,
    List<Comment> comments
  ) {
    this.postInfo = postInfo;
    this.comments = comments;
  }

}
