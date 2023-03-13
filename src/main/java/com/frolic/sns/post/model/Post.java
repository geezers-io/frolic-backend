package com.frolic.sns.post.model;

import com.frolic.sns.post.dto.CreatePostRequest;
import com.frolic.sns.global.util.models.BaseTimeAuditing.CreateAndModifiedTimeAuditEntity;
import com.frolic.sns.user.model.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Post extends CreateAndModifiedTimeAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 1000)
  private String textContent;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
  private final List<PostFile> postFiles = new ArrayList<>();

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
  private final List<PostHashTag> postHashTags = new ArrayList<>();

  @Builder(setterPrefix = "add")
  public Post(
    String textContent,
    User user
  ) {
    this.textContent = textContent;
    this.user = user;
  }

  @Deprecated
  public static Post createPost(CreatePostRequest dto, User user) {
    return Post.builder()
      .addUser(user)
      .addTextContent(dto.getTextContent())
      .build();
  }

  public void updateTextContent(String textContent) {
    // TODO: 2022-11-13 validation
    this.textContent = textContent;
  }

}
