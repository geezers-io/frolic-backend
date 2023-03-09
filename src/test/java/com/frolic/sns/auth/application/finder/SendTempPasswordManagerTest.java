package com.frolic.sns.auth.application.finder;

import com.frolic.sns.auth.application.finder.common.AuthCode;
import com.frolic.sns.auth.application.finder.common.FinderType;
import com.frolic.sns.auth.dto.VerifyCodeRequest;
import com.frolic.sns.auth.exception.MisMatchAuthCodeException;
import com.frolic.sns.auth.exception.OverTriedAuthCodeException;
import com.frolic.sns.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SendTempPasswordManagerTest {

    @Autowired
    protected SendTempPasswordManager sendTempPasswordManager;
    @Autowired
    protected UserRepository userRepository;

    UUID id;

    @BeforeEach
    void beforeEach() {
        id = sendTempPasswordManager.createId();
        String code = sendTempPasswordManager.createCode();
        sendTempPasswordManager.storeAuthCode(
                AuthCode.builder()
                        .addId(id)
                        .addCode(code)
                        .addDestination("01051998927")
                        .addCountOfAttempts(0)
                        .addFinderType(FinderType.PASSWORD)
                        .addLocalTime(LocalTime.now())
                        .build()
        );
    }

    @Test
    @DisplayName("사용자가 저장한 인증 코드 값을 정상적으로 불러온다.")
    void createAndLoadAuthCode() {
        // given

        // when
        AuthCode.MetaData metaData = sendTempPasswordManager.getAuthCode(id, FinderType.PASSWORD);

        // then
        Assertions.assertNotNull(metaData);
        System.out.println("metaData.destination: " + metaData.getDestination());
    }

    @Test
    @DisplayName("인증 코드 검증을 실패한다.")
    void verifyOnceFailed() {
        // given
        VerifyCodeRequest request = new VerifyCodeRequest("wrongCode");

        // then
        assertThrows(MisMatchAuthCodeException.class, () -> sendTempPasswordManager.verifyAuthCode(id, request));
    }

    @Test
    @DisplayName("인증 코드 시도가 5회 초과 시 에러가 발생한다.")
    void verifyOver() {
        // given
        VerifyCodeRequest request = new VerifyCodeRequest("wrongCode");

        // when
        for (int i = 0; i < 5; i++) {
            assertThrows(MisMatchAuthCodeException.class, () -> {
                sendTempPasswordManager.verifyAuthCode(id, request);
            });
        }

        // then
        assertThrows(OverTriedAuthCodeException.class, () -> sendTempPasswordManager.verifyAuthCode(id, request));
    }

    @Test
    @DisplayName("email, phoneNumber 정보가 일치하는 사용자가 있다.")
    void passwordMatchedTest(){
        //given
        String userEmail = "han14866@naver.com";
        String userPhoneNumber = "01051998927";
        //when
        String findemail = String.valueOf(userRepository.getUserInfoPwExist(userEmail, userPhoneNumber));
        //then
        //assertEquals(findemail, "Optional[han14866@naver.com]");
        assertEquals(findemail, userEmail);
    }

    @Test
    @DisplayName("email, phoneNumber 정보가 일치하는 사용자가 없다.")
    void passwordNotMatchedTest(){
        //given
        String userEmail = "hello@naver.com";
        String userPhoneNumber = "010-1234-5678";
        //when
        String email = String.valueOf(userRepository.getUserInfoPwExist(userEmail, userPhoneNumber));
        //then
        //assertEquals(email, userEmail);
        assertNotEquals(email, userEmail, "Not Matched");
    }
}
