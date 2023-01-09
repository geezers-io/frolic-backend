package com.modular.restfulserver.post.dto;

import static com.modular.restfulserver.global.util.message.FieldError.*;
import com.modular.restfulserver.user.dto.UserDetails;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetail {

  private final Long id;
  private final UserDetails userDetails;
  private final String textContent;
  private final List<CommentDetail> comments;
  private final List<String> hashtags;
  private final Long likeCount;
  private final List<String> fileDownloadUrls;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;
  private final boolean isLikeUp;

  @Builder(setterPrefix = "add")
  public PostDetail(
    Long id,
    String textContent,
    UserDetails userDetails,
    List<CommentDetail> comments,
    List<String> hashtags,
    Long likeCount,
    boolean isLikeUp,
    List<String> fileDownloadUrls,
    LocalDateTime createdDate,
    LocalDateTime updatedDate
  ) {
    Assert.isInstanceOf(Long.class, id, getIllegalFieldError("postId"));
    Assert.isInstanceOf(String.class, textContent, getIllegalFieldError("textContent"));
    Assert.isInstanceOf(UserDetails.class, userDetails, getIllegalFieldError("userInfo"));
    Assert.isInstanceOf(List.class, comments, getIllegalFieldError("comments"));
    Assert.isInstanceOf(List.class, hashtags, getIllegalFieldError("hashtags"));
    Assert.isInstanceOf(Long.class, likeCount, getIllegalFieldError("likeCount"));
    Assert.isInstanceOf(boolean.class, isLikeUp, getIllegalFieldError("isLikeUp"));
    Assert.isInstanceOf(List.class, fileDownloadUrls, getIllegalFieldError("fileDownloadUrls"));
    Assert.isInstanceOf(LocalDateTime.class, createdDate, getIllegalFieldError("createdDate"));
    Assert.isInstanceOf(LocalDateTime.class, updatedDate, getIllegalFieldError("updatedDate"));

    this.id = id;
    this.textContent = textContent;
    this.userDetails = userDetails;
    this.comments = comments;
    this.hashtags = hashtags;
    this.likeCount = likeCount;
    this.isLikeUp = isLikeUp;
    this.fileDownloadUrls = fileDownloadUrls;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

}
