package com.frolic.sns.auth.application;

import com.frolic.sns.auth.dto.UserFindEmailRequest;

public interface UserInfoFindable {

    String findEmail(String phoneNumber);
    UserFindEmailRequest findPassword(UserFindEmailRequest userFindPasswordRequest);
}
