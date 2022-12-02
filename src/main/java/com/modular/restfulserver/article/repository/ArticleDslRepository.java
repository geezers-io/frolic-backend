package com.modular.restfulserver.article.repository;

import com.modular.restfulserver.article.model.Article;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ArticleDslRepository {

  List<Article> findBySearchParamsByPagination(List<String> searchParams, Pageable pageable);

}
