package com.modular.restfulserver.post.repository;

import com.modular.restfulserver.post.model.Post;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostDslRepository {

  List<Post> findBySearchParamsByPagination(List<String> searchParams, Pageable pageable);

}
