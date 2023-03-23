package com.frolic.sns.user.application;

import com.frolic.sns.user.application.info.UserName;
import com.frolic.sns.user.dto.FollowUserRequest;
import com.frolic.sns.user.exception.AlreadyExistsFollowException;
import com.frolic.sns.user.model.Follow;
import com.frolic.sns.user.model.User;
import com.frolic.sns.user.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFollowService {

  private final FollowRepository followRepository;
  private final UserManager userManager;

  public List<FollowUserRequest> getFollowerListBySelf(User user) {
    return followRepository.findAllNameByUserFollowerInfo(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  public List<FollowUserRequest> getFollowerListByUsername(String username) {
    User user = userManager.getUser(new UserName(username));
    return followRepository.findAllFollowerUserByUsername(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  public List<FollowUserRequest> getFollowingListBySelf(User user) {
    return followRepository.findAllNameByUserFollowingInfo(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  public List<FollowUserRequest> getFollowingListByUsername(String username) {
    User user = userManager.getUser(new UserName(username));
    return followRepository.findAllFollowingUserByUsername(user).stream()
      .map(this::createFollowUser)
      .collect(Collectors.toList());
  }

  public void addFollowToUsername(User user, String username) {
    User targetUser = userManager.getUser(new UserName(username));
    boolean isExistsFollow = followRepository
      .existsFollowByFollowerIdAndFollowingId(targetUser, user);
    if (isExistsFollow)
      throw new AlreadyExistsFollowException();

    Follow newFollow = new Follow();
    newFollow.setFollowerId(targetUser);
    newFollow.setFollowingId(user);
    followRepository.save(newFollow);
  }

  public void removeFollowToUsername(User user, String username) {
    User targetUser = userManager.getUser(new UserName(username));
    followRepository.deleteFollowByFollowerIdAndFollowingId(targetUser, user);
  }

  public boolean checkExistsFollow(User user, String username) {
    User targetUser = userManager.getUser(new UserName(username));
    return followRepository.existsFollowByFollowerIdAndFollowingId(targetUser, user);
  }

  public boolean checkExistsFollowing(User user, String username) {
    User targetUser = userManager.getUser(new UserName(username));
    return followRepository.existsFollowByFollowerIdAndFollowingId(user, targetUser);
  }

  private FollowUserRequest createFollowUser(User user) {
    return new FollowUserRequest(user.getUsername(), user.getRealname());
  }


}
