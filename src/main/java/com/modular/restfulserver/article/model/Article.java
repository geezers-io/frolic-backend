package com.modular.restfulserver.article.model;

import com.modular.restfulserver.global.utils.models.BaseTimeAuditing.CreateAndModifiedTimeAuditEntity;
import com.modular.restfulserver.user.model.User;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

  @Column(length = 1000)
  private String textContent;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
  private final List<ArticleHashTag> articleHashTags = new ArrayList<>();

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
  private final List<File> files = new ArrayList<>();

  @Builder(setterPrefix = "add")
  public Article(
    String textContent,
    User user
  ) {
    this.textContent = textContent;
    this.user = user;
  }

  public void updateTextContent(String textContent) {
    // TODO: 2022-11-13 validation
    this.textContent = textContent;
  }

}
