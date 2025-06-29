package com.northEthio.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject,
                                      String text   ) throws MessagingException {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimMessageHelper = new MimeMessageHelper(
                    mimeMessage, "utf-8");
            mimMessageHelper.setSubject(subject);
            mimMessageHelper.setText(text);
            mimMessageHelper.setTo(userEmail);
            javaMailSender.send(mimeMessage);

        }catch (MailException e){
            System.out.println("error --- " + e);
            throw new MailSendException("faild to send email");
        }

    }
}
