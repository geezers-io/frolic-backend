package com.modular.restfulserver.article.dto;

import com.modular.restfulserver.global.exception.BuilderArgumentNotValidException;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SingleArticleInfoDto {

  private final Long postId;
  private final UserInfoForClientDto userInfo;
  private final String textContent;
  private final List<SingleCommentInfoDto> comments;
  private final List<String> hashtags;
  private final Long likeCount;
  private final List<String> fileDownloadUrls;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;

  @Builder(setterPrefix = "add")
  public SingleArticleInfoDto(
    Long postId,
    String textContent,
    UserInfoForClientDto userInfo,
    List<SingleCommentInfoDto> comments,
    List<String> hashtags,
    Long likeCount,
    List<String> fileDownloadUrls,
    LocalDateTime createdDate,
    LocalDateTime updatedDate
  ) {
    if (fileDownloadUrls == null)
      throw new BuilderArgumentNotValidException("[SingleArticleInfoDto] fileDownloadUrls is null");

    this.postId = postId;
    this.textContent = textContent;
    this.userInfo = userInfo;
    this.comments = comments;
    this.hashtags = hashtags;
    this.likeCount = likeCount;
    this.fileDownloadUrls = fileDownloadUrls;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

}
