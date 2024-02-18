package com.example.MedicalFriend.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public String sendVerificationCode(String mail) {
        SimpleMailMessage message = new SimpleMailMessage();

        String code = generateVerificationCode();

        message.setFrom(fromMail);
        message.setTo(mail);
        message.setSubject("Email Verification Code For Medical-Friend");
        message.setText("Your verification code is: " + code);

        javaMailSender.send(message);

        return code;
    }

    private static final int CODE_LENGTH = 6;

    private static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
