package com.modular.restfulserver.post.model;

import com.modular.restfulserver.global.util.models.BaseTimeAuditing.CreateAndModifiedTimeAuditEntity;
import com.modular.restfulserver.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment extends CreateAndModifiedTimeAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String textContent;

  @Column
  private Long replyUserPkId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @Builder(setterPrefix = "add")
  public Comment(
    User user,
    Post post,
    String textContent,
    Long replyUserPkId
  ) {
    Assert.hasText(textContent, "textContent field must be string");

    this.user = user;
    this.post = post;
    this.textContent = textContent;
    this.replyUserPkId = replyUserPkId;
  }

  public void updateTextContent(String content) {
    this.textContent = content;
  }

}
