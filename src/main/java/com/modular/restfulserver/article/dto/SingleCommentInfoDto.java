package com.modular.restfulserver.article.dto;

import com.modular.restfulserver.global.exception.BuilderArgumentNotValidException;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import lombok.Getter;

@Getter
public class SingleCommentInfoDto {

  private final Long commentId;

  private final Long articleId;

  private final Long replyUserId;

  private final String textContent;

  private final UserInfoForClientDto userInfo;

  public SingleCommentInfoDto(
    Long commentId,
    Long articleId,
    Long replyUserId,
    String textContent,
    UserInfoForClientDto userInfo
  ) {
    if (
      commentId == null
      || articleId == null
      || textContent == null
      || userInfo == null
    ) {
      throw new BuilderArgumentNotValidException("SingleCommentInfoDto 빌더 과정에서 인자가 잘못되었습니다.");
    }

    this.commentId = commentId;
    this.articleId = articleId;
    this.replyUserId = replyUserId;
    this.textContent = textContent;
    this.userInfo = userInfo;
  }

}
