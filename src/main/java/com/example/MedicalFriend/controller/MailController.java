package com.example.MedicalFriend.controller;

import com.example.MedicalFriend.service.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail) {
        return mailService.sendVerificationCode(mail);
    }
}
