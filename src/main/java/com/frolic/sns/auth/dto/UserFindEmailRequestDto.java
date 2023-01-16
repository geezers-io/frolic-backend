package com.frolic.sns.auth.dto;

import com.frolic.sns.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
public class UserFindEmailRequestDto {

    private String realname;
    private String phoneNumber;

    //email 찾기 로직
    public UserFindEmailRequestDto(String realname, String phoneNumber){
        this.realname = realname;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity() {
        return User.builder()
                .addRealname(realname)
                .addPhoneNumber(phoneNumber)
                .build();
    }

}
