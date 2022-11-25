package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.exception.AlreadyExistsLikeException;
import com.modular.restfulserver.article.model.Article;
import com.modular.restfulserver.article.model.Like;
import com.modular.restfulserver.article.repository.ArticleRepository;
import com.modular.restfulserver.article.repository.LikeRepository;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeManagerImpl implements PostLikeManager {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final ArticleRepository articleRepository;
  private final JwtProvider jwtProvider;

  @Override
  public Long likePostByTokenUser(String token, Long postId) {

    User user = getUserByToken(token);
    Article article = articleRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    if (likeRepository.existsByArticleAndUser(article, user))
      throw new AlreadyExistsLikeException();

    Like newLike = new Like(user, article);
    likeRepository.save(newLike);

    return likeRepository.countAllByArticle(article);
  }

  @Override
  public Long unLikePostByTokenUser(String token, Long postId) {
    User user = getUserByToken(token);
    Article article = articleRepository.findById(postId).orElseThrow(NotFoundResourceException::new);
    Like like = likeRepository.findByArticleAndUser(article, user).orElseThrow(NotFoundResourceException::new);

    likeRepository.delete(like);
    return likeRepository.countAllByArticle(article);
  }

  private User getUserByToken(String token) {
    return userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);
  }

}
