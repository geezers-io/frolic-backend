package com.modular.restfulserver.post.util;

import com.modular.restfulserver.global.utils.message.ValidationMessage;

public class ValidationMessages implements ValidationMessage {

  public static String notNullId = "id" + mustBeNotNullMessage;

  public static String notNullPostId = "postId" + mustBeNotNullMessage;

  public static String notNullTextContent = "textContent" + mustBeNotNullMessage;

  public static String notNullUserInfo = "userInfo" + mustBeNotNullMessage;

  public static String notNullOwnerId = "ownerId" + mustBeNotNullMessage;

}
