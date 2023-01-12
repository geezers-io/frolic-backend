package com.frolic.sns.auth.application;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthCodeCacheManager {

    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }
    public ResponseEntity<String> sendSMS(@PathVariable(name = "phoneNumber") String phoneNumber) {

        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        int authNum = randomRange(100000, 999999);

        String authMsg = "The authentication number is [" + authNum + "]" ;

        Message.creator(new PhoneNumber("+82"+phoneNumber),
                new PhoneNumber("+14807252713"), authMsg).create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }

}
