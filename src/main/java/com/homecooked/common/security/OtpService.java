package com.homecooked.common.security;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OtpService {

    //    @Value("@{twllio.sms.key}")
    private String smsKey = "";

    //    @Value("@{twllio.sms.user}")
    private String smsUser = "";

    //    @Value("@{twllio.sms.from}")
    private String fromPhoneNumber = "";
    private String serviceSid = "";

    @PostConstruct
    public void initTwilio() {
        Twilio.init(smsUser, smsKey);
    }

    public void sendOtpSMS(String toPhoneNumber) {
        Verification verification = Verification.creator(serviceSid, toPhoneNumber, "sms").setLocale("en").create();
        System.out.println("Verification sent: " + verification.getSid());
        Twilio.init(smsUser, smsKey);
    }

    public boolean verifyOtp(String phoneNumber, String code) {

        VerificationCheck verificationCheck = VerificationCheck.creator(
                        serviceSid)
                .setCode(code)
                .setTo(phoneNumber).create();
        if ("approved".equals(verificationCheck.getStatus())) {
            return true;
        } else {
            throw new RuntimeException("Verification failed.");
        }


    }
}
