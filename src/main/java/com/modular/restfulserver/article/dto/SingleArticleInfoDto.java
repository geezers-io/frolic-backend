package com.modular.restfulserver.article.dto;

import com.modular.restfulserver.article.model.Comment;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SingleArticleInfoDto {

  private final Long postId;
  private final UserInfoForClientDto userInfo;
  private final String title;
  private final String textContent;
  private final List<Comment> comments;
  private final List<String> hashtags;
  private final Long likeCount;

  @Builder(setterPrefix = "add")
  public SingleArticleInfoDto(
    Long postId,
    String title,
    String textContent,
    UserInfoForClientDto userInfo,
    List<Comment> comments,
    List<String> hashtags,
    Long likeCount
  ) {
    this.postId = postId;
    this.title = title;
    this.textContent = textContent;
    this.userInfo = userInfo;
    this.comments = comments;
    this.hashtags = hashtags;
    this.likeCount = likeCount;
  }

}
