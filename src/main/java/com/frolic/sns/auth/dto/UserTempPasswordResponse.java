package com.frolic.sns.auth.dto;

import lombok.Getter;

@Getter
public class UserTempPasswordResponse {
    private final String password;

    public UserTempPasswordResponse(String password ) {
        this.password = password;
    }
}
