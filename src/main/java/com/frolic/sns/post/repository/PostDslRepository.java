package com.frolic.sns.post.repository;

import com.frolic.sns.post.model.Post;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostDslRepository {

  List<Post> findBySearchParamsByPagination(List<String> searchParams, Long cursorId, Pageable pageable);

}
