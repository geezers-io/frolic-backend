package com.frolic.sns.auth.application;

import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.frolic.sns.auth.dto.UserFindPasswordRequest;

public interface UserInfoFindable {

    String findEmail(String phoneNumber);
    UserFindPasswordRequest findPassword(UserFindPasswordRequest userFindPasswordRequest);
}
