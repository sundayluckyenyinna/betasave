package com.pentazon.betasave.utils;

import com.pentazon.betasave.dto.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil{

    @Autowired
    private Environment env;
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(MailData mailData) throws MailException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
        try {
            messageHelper.setText(mailData.getContent(), true);
            messageHelper.setFrom(env.getProperty("spring.mail.username"));
            messageHelper.setTo(mailData.getRecipientMail());
            messageHelper.setSubject(mailData.getSubject());
            mailSender.send(message);
        } catch (MessagingException ignored) {}
    }

}
