package com.pentazon.betasave.utils;

import com.pentazon.betasave.dto.MailData;
import com.pentazon.betasave.dto.OtpSendInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class OtpUtil
{
    @Autowired
    private Environment env;

    @Autowired
    private MailUtil mailUtil;

    private static final int OTP_LENGTH = 6;

    private String generateOtp(){
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i = 0; i < OTP_LENGTH; i++){
            int randomInteger = (int)(Math.random() * 10);
            stringBuilder.append(randomInteger);
        }
        return stringBuilder.toString();
    }

    public OtpSendInfo sendSignUpOtpToMail(String email){
        String fileName = "otp-signup";
        String expirationTime = env.getProperty("betasave.otp.signup.expirationTimeInMin");
        OtpSendInfo otpSendInfo = new OtpSendInfo();
        try {
            String signupTemplate = MessageTemplateUtil.getTemplateOf(fileName);
            String otp = generateOtp();
            otpSendInfo.setOtpSent(otp);
            otpSendInfo.setRecipientEmail(email);
            otpSendInfo.setCreatedDateTime(LocalDateTime.now());
            otpSendInfo.setExpirationDateTime(otpSendInfo.getCreatedDateTime().plusMinutes(Integer.parseInt(expirationTime) ));
            signupTemplate = signupTemplate.replace("{otp}", otp)
                    .replace("{otpExpiration}", expirationTime);

            MailData mailData = new MailData();
            mailData.setRecipientMail(email);
            mailData.setSubject("Betasave Email Verification");
            mailData.setContent(signupTemplate);
            mailUtil.sendNotification(mailData);
        } catch (IOException ignored) {
            System.out.println(ignored);
        }

        return otpSendInfo;
    }

    public OtpSendInfo sendForgetPasswordOtpToMail(String email){
        String fileName = "otp-forgotpassword";
        String expirationTime = env.getProperty("betasave.otp.signup.expirationTimeInMin");
        OtpSendInfo otpSendInfo = new OtpSendInfo();
        try {
            String signupTemplate = MessageTemplateUtil.getTemplateOf(fileName);
            String otp = generateOtp();
            otpSendInfo.setOtpSent(otp);
            otpSendInfo.setRecipientEmail(email);
            otpSendInfo.setCreatedDateTime(LocalDateTime.now());
            otpSendInfo.setExpirationDateTime(otpSendInfo.getCreatedDateTime().plusMinutes(Integer.parseInt(expirationTime)));
            signupTemplate = signupTemplate.replace("{otp}", otp)
                    .replace("{otpExpiration}", expirationTime);

            MailData mailData = new MailData();
            mailData.setRecipientMail(email);
            mailData.setSubject("Betasave Verification Email");
            mailData.setContent(signupTemplate);
            mailUtil.sendNotification(mailData);
        } catch (IOException ignored) {
            System.out.println(ignored);
        }

        return otpSendInfo;
    }
}
