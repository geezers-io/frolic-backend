package com.frolic.sns.auth.dto;

import com.frolic.sns.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindEmailRequest {
    private String phoneNumber;
    
    public UserFindEmailRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public User toEntity() {
        return User.builder()
                .addPhoneNumber(phoneNumber)
                .build();
    }

}
