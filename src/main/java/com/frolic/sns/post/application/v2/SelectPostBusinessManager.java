package com.frolic.sns.post.application.v2;

import com.frolic.sns.global.common.file.dto.FileInfo;
import com.frolic.sns.post.dto.CommentInfo;
import com.frolic.sns.post.dto.v2.PostInfo;
import com.frolic.sns.post.model.Comment;
import com.frolic.sns.post.model.Post;
import com.frolic.sns.post.repository.CommentRepository;
import com.frolic.sns.post.repository.LikeRepository;
import com.frolic.sns.post.repository.PostFileRepository;
import com.frolic.sns.post.repository.PostHashtagRepository;
import com.frolic.sns.user.dto.UserInfo;
import com.frolic.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SelectPostBusinessManager {
    private final PostHashtagRepository postHashtagRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    private final PostFileRepository postFileRepository;
    public PostInfo getListOfSingleArticleDtoByPageResults(Page<Post> articlePage, User user) {
        List<String> hashtags = postHashtagRepository.findAllByPost((Post) articlePage);
        UserInfo articleOwner = UserInfo.from(((Post) articlePage).getUser());
        PostInfo getPostInfo = getSingleArticleDto((Post) articlePage, hashtags, articleOwner, user);

        return getPostInfo;
    }

    private PostInfo getSingleArticleDto(Post post, List<String> hashtags, UserInfo userInfo, User user) {
        long likeCount = likeRepository.countAllByPost(post);
        List<CommentInfo> comments = commentRepository.findAllByPost(post)
                .stream()
                .map(comment -> getSingleCommentDtoByEntity(comment, post))
                .collect(Collectors.toList());
        long commentCount = commentRepository.countAllByPost(post);
        //List<FileInfo> files = postFileRepository.getFilesByPostId(post.getId());
        boolean isLikeUp = likeRepository.existsByPostAndUser(post, user);

        return PostInfo.builder()
                .addId(post.getId())
                .addHashtags(hashtags)
                .addCommentCount(commentCount)
                .addUserInfo(userInfo)
                .addIsLikeUp(isLikeUp)
                .addLikeCount(likeCount)
                .addTextContent(post.getTextContent())
                //.addFiles(files)
                .addCreatedDate(post.getCreatedDate())
                .addUpdatedDate(post.getUpdatedDate())
                .build();
    }

    private CommentInfo getSingleCommentDtoByEntity(Comment comment, Post post) {
        return CommentInfo.builder()
                .addId(comment.getId())
                .addReplyUserId(comment.getReplyUserPkId())
                .addPostId(post.getId())
                .addUserInfo(UserInfo.from(comment.getUser()))
                .addTextContent(comment.getTextContent())
                .build();
    }

}