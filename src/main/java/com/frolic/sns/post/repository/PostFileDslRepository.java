package com.frolic.sns.post.repository;

import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.global.common.file.model.QApplicationFile;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
import com.frolic.sns.post.model.QPostFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.frolic.sns.global.common.file.model.QApplicationFile.applicationFile;
import static com.frolic.sns.post.model.QPostFile.postFile;

@Repository
@RequiredArgsConstructor
public class PostFileDslRepository {

  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;


  public List<PostFile> createPostFilesByIds(Post post, List<Long> fileIds) {
    List<ApplicationFile> files = queryFactory.selectFrom(applicationFile)
        .where(applicationFile.id.in(fileIds))
        .fetch();
    files.forEach(file -> {
        PostFile postFile = PostFile.builder().addPost(post).addFile(file).build();
        entityManager.persist(postFile);
      }
    );
    return queryFactory.selectFrom(postFile)
      .where(postFile.id.in(fileIds))
      .fetch();
  }

}
