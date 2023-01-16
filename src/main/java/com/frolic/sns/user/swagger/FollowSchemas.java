package com.frolic.sns.user.swagger;

import com.frolic.sns.user.dto.FollowUserRequest;
import lombok.Getter;

import java.util.List;

public class FollowSchemas {

  @Getter
  public static class FollowUserSchema {
    List<FollowUserRequest> data;
  }

  @Getter
  public static class isFollowSchema {

    @Getter
    static class FollowExists {
      Boolean isFollow;
    }

    FollowExists data;
  }

  @Getter
  public static class isFollowingSchema {

    @Getter
    static class FollowingExists {
      Boolean isFollowing;
    }

    FollowingExists data;
  }

}
