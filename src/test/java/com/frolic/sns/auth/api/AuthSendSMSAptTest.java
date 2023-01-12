package com.frolic.sns.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frolic.sns.auth.dto.UserFindEmailRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthSendSMSAptTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthApi authApi;

    UserFindEmailRequest userFindEmailRequest;

    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }
    @Test
    @DisplayName("Twilio로 메세지 전송")
    void sendSMS() throws Exception{
        //given
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        //when
        int authNum = randomRange(100000, 999999);

        String authMsg = "The authentication number is [" + authNum + "]" ;

        Message message = Message.creator(
                // to
                new PhoneNumber("+821051998927"),
                // from
                new PhoneNumber("+14807252713"),
                // message
                authMsg).create();
    }

}
