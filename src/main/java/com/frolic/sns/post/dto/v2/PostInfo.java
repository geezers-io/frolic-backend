package com.frolic.sns.post.dto.v2;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.model.Hashtag;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
import com.frolic.sns.post.model.PostHashTag;
import com.frolic.sns.user.dto.UserInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.frolic.sns.global.util.message.CommonMessageUtils.getIllegalFieldError;

@Getter
public class PostInfo {

  private final Long id;
  private final UserInfo userInfo;
  private final String textContent;
  private final Long commentCount;
  private final List<String> hashtags;
  private final Long likeCount;
  private final List<FileInfo> files;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;
  private final boolean isLikeUp;

  @Builder(setterPrefix = "add")
  public PostInfo(
    Long id,
    String textContent,
    UserInfo userInfo,
    Long commentCount,
    List<String> hashtags,
    Long likeCount,
    boolean isLikeUp,
    List<FileInfo> files,
    LocalDateTime createdDate,
    LocalDateTime updatedDate
  ) {
    Assert.isInstanceOf(Long.class, id, getIllegalFieldError("postId"));
    Assert.isInstanceOf(String.class, textContent, getIllegalFieldError("textContent"));
    Assert.isInstanceOf(UserInfo.class, userInfo, getIllegalFieldError("userInfo"));
    Assert.isInstanceOf(Long.class, commentCount, getIllegalFieldError("commentCount"));
    Assert.isInstanceOf(Long.class, likeCount, getIllegalFieldError("likeCount"));
    Assert.notNull(files, getIllegalFieldError("files"));
    Assert.isInstanceOf(LocalDateTime.class, createdDate, getIllegalFieldError("createdDate"));
    Assert.isInstanceOf(LocalDateTime.class, updatedDate, getIllegalFieldError("updatedDate"));

    this.id = id;
    this.textContent = textContent;
    this.userInfo = userInfo;
    this.commentCount = commentCount;
    this.hashtags = Objects.requireNonNullElseGet(hashtags, ArrayList::new);
    this.likeCount = likeCount;
    this.isLikeUp = isLikeUp;
    this.files = files;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  public static PostInfoBuilder addProperties(Post post) {
    return PostInfo.builder()
      .addId(post.getId())
      .addTextContent(post.getTextContent())
      .addHashtags(
        post.getPostHashTags().stream()
          .map(PostHashTag::getHashtag)
          .map(Hashtag::getName)
          .collect(Collectors.toList())
      )
      .addFiles(
        post.getPostFiles().stream()
          .map(PostFile::getFile)
          .map(FileInfo::from)
          .collect(Collectors.toList())
      )
      .addCreatedDate(post.getCreatedDate())
      .addUpdatedDate(post.getUpdatedDate());
  }

}
