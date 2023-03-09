package com.frolic.sns.auth.api;

import com.frolic.sns.auth.application.finder.SendTempPasswordManager;
import com.frolic.sns.auth.dto.UserTempPasswordRequest;
import com.frolic.sns.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserTempPasswordApiTest {

    protected MockMvc mockMvc;
    @Autowired
    protected SendTempPasswordManager sendTempPasswordManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 정보가 맞는 경우 성공한다.")
    void checkUserInfo() throws Exception {
        //작성중..ing
        //given
        UserTempPasswordRequest request = new UserTempPasswordRequest("han14866@naver.com","01051998927");

        // when
        String userEmail = String.valueOf(userRepository.getUserInfoPwExist(request.getEmail(), request.getPhoneNumber()));

        // then
        Assertions.assertNotNull(userEmail);
    }

}
