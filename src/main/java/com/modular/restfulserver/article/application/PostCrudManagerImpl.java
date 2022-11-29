package com.modular.restfulserver.article.application;

import com.modular.restfulserver.article.dto.CreatePostRequestDto;
import com.modular.restfulserver.article.dto.SingleArticleInfoDto;
import com.modular.restfulserver.article.dto.SingleCommentInfoDto;
import com.modular.restfulserver.article.dto.UpdateArticleRequestDto;
import com.modular.restfulserver.article.model.*;
import com.modular.restfulserver.article.repository.*;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.dto.UserInfoForClientDto;
import com.modular.restfulserver.user.exception.NotPermissionException;
import com.modular.restfulserver.user.exception.UserNotFoundException;
import com.modular.restfulserver.user.model.User;
import com.modular.restfulserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostCrudManagerImpl implements PostCrudManager {

  private final ArticleFileManager articleFileManager;
  private final ArticleRepository articleRepository;
  private final HashtagRepository hashtagRepository;
  private final ArticleHashtagRepository articleHashtagRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;

  private final FileRepository fileRepository;
  private final JwtProvider jwtProvider;

  @Override
  public SingleArticleInfoDto getPostById(Long id) {
    Article article = articleRepository.findById(id).orElseThrow(NotFoundResourceException::new);

    List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
    UserInfoForClientDto userInfo = getUserInfoForClientDto(article.getUser());

    return getSingleArticleDto(article, hashtags, userInfo);
  }

  // TODO: 2022-11-29 여러 번 발생하는 쿼리를 하나로 줄일 수 없을까? 
  // TODO: 2022-11-29 복잡한 코드를 줄이자
  @Override
  public SingleArticleInfoDto updatePostById(
    String token,
    Long id,
    UpdateArticleRequestDto singleArticleInfoDto,
    List<CustomFile> files
  ) {
    Article article = verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    List<String> createdFileDownloadUrls = articleFileManager.saveFilesWithArticle(article, files);

    article.updateTextContent(singleArticleInfoDto.getTextContent()); // 본문 갱신


    // 파일 삭제내용이 있다면 게시글 연관 파일 삭제
    int articleOwnedFileSize = article.getFiles().size();
    int updateRequestFileSize = singleArticleInfoDto.getFileDownloadUrls().size();

    if (articleOwnedFileSize != updateRequestFileSize) {
      List<String> updatedFilenames = singleArticleInfoDto.getFileDownloadUrls().stream()
        .map(CustomFile::parseFilenameByDownloadUrls)
        .collect(Collectors.toList());

      if (files != null) {
        List<String> createdFilenames = createdFileDownloadUrls.stream()
          .map(CustomFile::parseFilenameByDownloadUrls)
          .collect(Collectors.toList());
        updatedFilenames.addAll(createdFilenames);
      }

      List<File> filteredFiles = fileRepository.findAllByArticle(article).stream()
        .filter(file -> !updatedFilenames.contains(file.getName()))
        .collect(Collectors.toList());
      fileRepository.deleteAll(filteredFiles);
      article.updateFiles(fileRepository.findAllByArticle(article));
      articleRepository.save(article);
    }

    // 해시태그 삭제된 리스트 검사 및 삭제
    List<String> updatedHashtagsNames = singleArticleInfoDto.getHashtags();
    List<String> articleOwnedHashtagNames = articleHashtagRepository.findAllByArticle(article);

    List<String> deletionHashtagNames = articleOwnedHashtagNames.stream()
        .filter(articleHashtag -> !updatedHashtagsNames.contains(articleHashtag))
        .collect(Collectors.toList());
    List<Hashtag> deletionHashtags = hashtagRepository.findAllByNameIn(deletionHashtagNames);
    deletionHashtags.forEach(hashtag -> articleHashtagRepository.deleteByArticleAndHashtag(article, hashtag));

    // 신규 등록된 해시태그 생성 및 게시글과 관계 생성
    updatedHashtagsNames.forEach(hashtag -> {
      createHashtagIfNotExists(hashtag);
      setRelationTagWithArticle(article, hashtag);
    });

    return getSingleArticleDto(article, updatedHashtagsNames, getUserInfoForClientDto(article.getUser()));
  }

  @Override
  public void deletePostById(String token, Long id) {
    verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    articleRepository.deleteById(id);
  }

  @Override
  public SingleArticleInfoDto createPost(String token, CreatePostRequestDto dto, List<CustomFile> files) {
    User user = getUserIsTokenAble(token);
    List<String> hashtags = dto.getHashtags();
    Article newArticle = Article.createArticle(dto, user);
    articleRepository.save(newArticle);
    List<String> fileDownloadUrls = articleFileManager.saveFilesWithArticle(newArticle, files);
    hashtags.forEach(tag -> {
      createHashtagIfNotExists(tag);
      setRelationTagWithArticle(newArticle, tag);
    });
    UserInfoForClientDto userInfo = getUserInfoForClientDto(user);

    return SingleArticleInfoDto.builder()
      .addPostId(newArticle.getId())
      .addTextContent(newArticle.getTextContent())
      .addComments(new ArrayList<>())
      .addHashtags(hashtags)
      .addLikeCount(0L)
      .addUserInfo(userInfo)
      .addFileDownloadUrls(fileDownloadUrls)
      .addCreatedDate(newArticle.getCreatedDate())
      .addUpdatedDate(newArticle.getUpdatedDate())
      .build();
  }

  @Override
  public List<SingleArticleInfoDto> getPostByTokenAndPagination(String token, Pageable pageable) {
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);

    Page<Article> articlePage = articleRepository.findAllByUserOrderByCreatedDateDesc(user, pageable);
    return getListOfSingleArticleDtoByPageResults(articlePage);
  }

  @Override
  public List<SingleArticleInfoDto> getEntirePostByPagination(Pageable pageable) {
    Page<Article> articlePage = articleRepository.findAllCreatedDateDesc(pageable);
    return getListOfSingleArticleDtoByPageResults(articlePage);
  }

  @Override
  public List<SingleArticleInfoDto> getSearchParamByPagination(List<String> searchList, Pageable pageable) {
    Page<Article> articlePage = articleRepository.findAllByHashtagsAndPagination(searchList, pageable);
    return getListOfSingleArticleDtoByPageResults(articlePage);
  }

  private Article verifyAndGetArticleIfUserRequestTargetHavePermission(String token, Long articleId) {
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);
    Article article = articleRepository.findById(articleId).orElseThrow(NotFoundResourceException::new);
    Long articleUserId = article.getUser().getId();

    if (!Objects.equals(user.getId(), articleUserId))
      throw new NotPermissionException();

    return article;
  }

  private User getUserIsTokenAble(String token) {
    return userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);
  }

  private SingleArticleInfoDto getSingleArticleDto(Article article, List<String> hashtags, UserInfoForClientDto userInfo) {
    long likeCount = likeRepository.countAllByArticle(article);
    List<SingleCommentInfoDto> comments = commentRepository.findAllByArticle(article)
      .stream()
      .map(comment -> getSingleCommentDtoByEntity(comment, article, userInfo))
      .collect(Collectors.toList());
    List<String> fileDownloadUrls = articleFileManager.getFileDownloadUrlsByArticle(article);

    return SingleArticleInfoDto.builder()
      .addPostId(article.getId())
      .addHashtags(hashtags)
      .addComments(comments)
      .addUserInfo(userInfo)
      .addLikeCount(likeCount)
      .addTextContent(article.getTextContent())
      .addFileDownloadUrls(fileDownloadUrls)
      .addCreatedDate(article.getCreatedDate())
      .addUpdatedDate(article.getUpdatedDate())
      .build();
  }

  private SingleCommentInfoDto getSingleCommentDtoByEntity(Comment comment, Article article, UserInfoForClientDto userInfo) {
    return SingleCommentInfoDto.builder()
      .addCommentId(comment.getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addArticleId(article.getId())
      .addUserInfo(userInfo)
      .addTextContent(comment.getTextContent())
      .build();
  }

  private UserInfoForClientDto getUserInfoForClientDto(User user) {
    return UserInfoForClientDto.builder()
      .addUserId(user.getId())
      .addEmail(user.getEmail())
      .addUsername(user.getUsername())
      .addRealname(user.getRealname())
      .build();
  }

  private void createHashtagIfNotExists(String tag) {
    if (!hashtagRepository.existsByName(tag)) {
      hashtagRepository.save(
        Hashtag.builder()
          .addName(tag)
          .build()
      );
    }
  }

  private void setRelationTagWithArticle(Article article, String tag) {
    Hashtag tagEntity = hashtagRepository.findByName(tag)
      .orElseThrow(NotFoundResourceException::new);
    boolean isAlreadyExistsRelation = articleHashtagRepository.existsByArticleAndHashtag(article, tagEntity);
    if (!isAlreadyExistsRelation)
      articleHashtagRepository.save(
        ArticleHashTag.builder()
          .addArticle(article)
          .addHashtag(tagEntity)
          .build()
      );
  }

  private List<SingleArticleInfoDto> getListOfSingleArticleDtoByPageResults(Page<Article> articlePage) {
    return articlePage.stream()
      .map(article -> {
        List<String> hashtags = articleHashtagRepository.findAllByArticle(article);
        UserInfoForClientDto articleOwner = getUserInfoForClientDto(article.getUser());
        return getSingleArticleDto(article,hashtags, articleOwner);
      })
      .collect(Collectors.toList());
  }

}
