package com.modular.restfulserver.article.model;

import com.modular.restfulserver.global.utils.models.BaseTimeAuditing.CreateAndModifiedTimeAuditEntity;
import com.modular.restfulserver.user.model.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "articles")
@SQLDelete(sql = "UPDATE articles SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Article extends CreateAndModifiedTimeAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(length = 1000)
  private String textContent;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "article")
  private final List<ArticleHashTag> articleHashTags = new ArrayList<>();

  @Builder(setterPrefix = "add")
  public Article(
    String title,
    String textContent,
    User user
  ) {
    Assert.hasText(title, "title field must be string");
    this.title = title;
    this.textContent = textContent;
    this.user = user;
  }

  public void updateTitle(String title) {
    // TODO: 2022-11-13 validation
    this.title = title;
  }

  public void updateTextContent(String textContent) {
    // TODO: 2022-11-13 validation
    this.textContent = textContent;
  }

}
