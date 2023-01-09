package com.modular.restfulserver.post.application;

import com.modular.restfulserver.post.dto.CreatePostRequest;
import com.modular.restfulserver.post.dto.PostDetail;
import com.modular.restfulserver.post.dto.CommentDetail;
import com.modular.restfulserver.post.dto.UpdatePostRequest;
import com.modular.restfulserver.post.model.*;
import com.modular.restfulserver.post.repository.*;
import com.modular.restfulserver.global.common.file.application.CustomFile;
import com.modular.restfulserver.global.config.security.JwtProvider;
import com.modular.restfulserver.global.exception.NotFoundResourceException;
import com.modular.restfulserver.user.dto.UserDetails;
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

  private final PostFileManager articleFileManager;
  private final PostRepository postRepository;
  private final HashtagRepository hashtagRepository;
  private final PostHashtagRepository postHashtagRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;

  private final FileRepository fileRepository;
  private final JwtProvider jwtProvider;

  @Override
  public PostDetail getPostById(Long id, String token) {
    Post post = postRepository.findById(id).orElseThrow(NotFoundResourceException::new);
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token))
      .orElseThrow(UserNotFoundException::new);

    List<String> hashtags = postHashtagRepository.findAllByPost(post);
    UserDetails userDetails = UserDetails.from(post.getUser());

    return getSingleArticleDto(post, hashtags, userDetails, user);
  }

  // TODO: 2022-11-29 여러 번 발생하는 쿼리를 하나로 줄일 수 없을까? 
  // TODO: 2022-11-29 복잡한 코드를 줄이자
  @Override
  public PostDetail updatePostById(
    String token,
    Long id,
    UpdatePostRequest singleArticleInfoDto,
    List<CustomFile> files
  ) {
    Post post = verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token))
      .orElseThrow(UserNotFoundException::new);
    List<String> createdFileDownloadUrls = articleFileManager.saveFilesWithArticle(post, files);

    post.updateTextContent(singleArticleInfoDto.getTextContent()); // 본문 갱신

    // 파일 삭제내용이 있다면 게시글 연관 파일 삭제
    int articleOwnedFileSize = post.getFiles().size();
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

      List<File> filteredFiles = fileRepository.findAllByPost(post).stream()
        .filter(file -> !updatedFilenames.contains(file.getName()))
        .collect(Collectors.toList());
      fileRepository.deleteAll(filteredFiles);
      post.updateFiles(fileRepository.findAllByPost(post));
      postRepository.save(post);
    }

    // 해시태그 삭제된 리스트 검사 및 삭제
    List<String> updatedHashtagsNames = singleArticleInfoDto.getHashtags();
    List<String> articleOwnedHashtagNames = postHashtagRepository.findAllByPost(post);

    List<String> deletionHashtagNames = articleOwnedHashtagNames.stream()
        .filter(articleHashtag -> !updatedHashtagsNames.contains(articleHashtag))
        .collect(Collectors.toList());
    List<Hashtag> deletionHashtags = hashtagRepository.findAllByNameIn(deletionHashtagNames);
    deletionHashtags.forEach(hashtag -> postHashtagRepository.deleteByPostAndHashtag(post, hashtag));
    User articleUser = post.getUser();

    // 신규 등록된 해시태그 생성 및 게시글과 관계 생성
    updatedHashtagsNames.forEach(hashtag -> {
      createHashtagIfNotExists(hashtag);
      setRelationTagWithArticle(post, hashtag);
    });

    return getSingleArticleDto(post, updatedHashtagsNames, UserDetails.from(articleUser), user);
  }

  @Override
  public void deletePostById(String token, Long id) {
    verifyAndGetArticleIfUserRequestTargetHavePermission(token, id);
    postRepository.deleteById(id);
  }

  @Override
  public PostDetail createPost(String token, CreatePostRequest createInfo, List<CustomFile> files) {
    User user = getUserIsTokenAble(token);
    List<String> hashtags = createInfo.getHashtags();
    Post newPost = Post.createPost(createInfo, user);
    postRepository.save(newPost);
    List<String> fileDownloadUrls = articleFileManager.saveFilesWithArticle(newPost, files);
    hashtags.forEach(tag -> {
      createHashtagIfNotExists(tag);
      setRelationTagWithArticle(newPost, tag);
    });
    UserDetails userDetails = UserDetails.from(user);

    return PostDetail.builder()
      .addId(newPost.getId())
      .addTextContent(newPost.getTextContent())
      .addComments(new ArrayList<>())
      .addHashtags(hashtags)
      .addLikeCount(0L)
      .addUserDetails(userDetails)
      .addFileDownloadUrls(fileDownloadUrls)
      .addCreatedDate(newPost.getCreatedDate())
      .addUpdatedDate(newPost.getUpdatedDate())
      .addIsLikeUp(false)
      .build();
  }

  @Override
  public List<PostDetail> getPostByTokenAndPagination(String token, Pageable pageable) {
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);

    Page<Post> articlePage = postRepository.findAllByUserOrderByCreatedDateDesc(user, pageable);
    return getListOfSingleArticleDtoByPageResults(articlePage, user);
  }

  @Override
  public List<PostDetail> getEntirePostByPagination(Pageable pageable, String token) {
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token))
      .orElseThrow(UserNotFoundException::new);
    Page<Post> articlePage = postRepository.findAllCreatedDateDesc(pageable);
    return getListOfSingleArticleDtoByPageResults(articlePage, user);
  }

  @Override
  public List<PostDetail> getSearchParamByPagination(List<String> searchList, Pageable pageable, String token) {
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token))
      .orElseThrow(UserNotFoundException::new);
    Page<Post> articlePage = postRepository.findAllByHashtagsAndPagination(searchList, pageable);
    return getListOfSingleArticleDtoByPageResults(articlePage, user);
  }

  private Post verifyAndGetArticleIfUserRequestTargetHavePermission(String token, Long articleId) {
    User user = userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);
    Post post = postRepository.findById(articleId).orElseThrow(NotFoundResourceException::new);
    Long articleUserId = post.getUser().getId();

    if (!Objects.equals(user.getId(), articleUserId))
      throw new NotPermissionException();

    return post;
  }

  private User getUserIsTokenAble(String token) {
    return userRepository.findByEmail(jwtProvider.getUserEmailByToken(token)).orElseThrow(UserNotFoundException::new);
  }

  private PostDetail getSingleArticleDto(Post post, List<String> hashtags, UserDetails userDetails, User user) {
    long likeCount = likeRepository.countAllByPost(post);
    List<CommentDetail> comments = commentRepository.findAllByPost(post)
      .stream()
      .map(comment -> getSingleCommentDtoByEntity(comment, post))
      .collect(Collectors.toList());
    List<String> fileDownloadUrls = articleFileManager.getFileDownloadUrlsByArticle(post);
    boolean isLikeUp = likeRepository.existsByPostAndUser(post, user);

    return PostDetail.builder()
      .addId(post.getId())
      .addHashtags(hashtags)
      .addComments(comments)
      .addUserDetails(userDetails)
      .addIsLikeUp(isLikeUp)
      .addLikeCount(likeCount)
      .addTextContent(post.getTextContent())
      .addFileDownloadUrls(fileDownloadUrls)
      .addCreatedDate(post.getCreatedDate())
      .addUpdatedDate(post.getUpdatedDate())
      .build();
  }

  private CommentDetail getSingleCommentDtoByEntity(Comment comment, Post post) {
    return CommentDetail.builder()
      .addId(comment.getId())
      .addReplyUserId(comment.getReplyUserPkId())
      .addPostId(post.getId())
      .addUserDetails(UserDetails.from(comment.getUser()))
      .addTextContent(comment.getTextContent())
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

  private void setRelationTagWithArticle(Post post, String tag) {
    Hashtag tagEntity = hashtagRepository.findByName(tag)
      .orElseThrow(NotFoundResourceException::new);
    boolean isAlreadyExistsRelation = postHashtagRepository.existsByPostAndHashtag(post, tagEntity);
    if (!isAlreadyExistsRelation)
      postHashtagRepository.save(
        PostHashTag.builder()
          .addPost(post)
          .addHashtag(tagEntity)
          .build()
      );
  }

  private List<PostDetail> getListOfSingleArticleDtoByPageResults(Page<Post> articlePage, User user) {
    return articlePage.stream()
      .map(article -> {
        List<String> hashtags = postHashtagRepository.findAllByPost(article);
        UserDetails articleOwner = UserDetails.from(article.getUser());
        return getSingleArticleDto(article,hashtags, articleOwner, user);
      })
      .collect(Collectors.toList());
  }

}
