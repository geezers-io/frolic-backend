package com.frolic.sns.post.model;

import com.frolic.sns.global.common.file.model.ApplicationFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name =  "post_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "file_id")
  private ApplicationFile file;

  @Builder(setterPrefix = "add")
  public PostFile(Post post, ApplicationFile file) {
    this.post = post;
    this.file = file;
  }

}
