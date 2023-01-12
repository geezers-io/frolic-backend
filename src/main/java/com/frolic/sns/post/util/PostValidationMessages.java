package com.frolic.sns.post.util;

import com.frolic.sns.global.util.message.ValidationMessage;

public class PostValidationMessages implements ValidationMessage {

  public static String notNullId = "id" + mustBeNotNullMessage;

  public static String notNullPostId = "postId" + mustBeNotNullMessage;

  public static String notNullTextContent = "textContent" + mustBeNotNullMessage;

  public static String notNullUserInfo = "userInfo" + mustBeNotNullMessage;

  public static String notNullOwnerId = "ownerId" + mustBeNotNullMessage;

}
