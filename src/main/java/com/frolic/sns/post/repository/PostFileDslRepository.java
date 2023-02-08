package com.frolic.sns.post.repository;

import com.frolic.sns.global.common.file.model.ApplicationFile;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.model.PostFile;
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
      .where(postFile.file.id.in(fileIds), postFile.post.id.eq(post.getId()))
      .fetch();
  }

  public List<Long> getIdsByIds(List<Long> ids) {
    return queryFactory.select(postFile.id)
      .where(postFile.file.id.in(ids))
      .fetch();
  }

  public List<Long> getIdsByPostId(Long id) {
    return queryFactory.select(postFile.id)
      .from(postFile)
      .where(postFile.post.id.eq(id))
      .fetch();
  }

  public void removeAllByIds(List<Long> ids) {
    queryFactory.delete(postFile)
      .where(postFile.id.in(ids))
      .execute();
  }

  public Boolean exists(Post post, Long id) {
    Long fetchOne = queryFactory.from(postFile)
      .where(postFile.post.id.eq(post.getId()), postFile.id.eq(id))
      .select(postFile.id)
      .fetchFirst();
    return fetchOne != null;
  }

}
