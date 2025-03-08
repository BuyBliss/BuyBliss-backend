package com.commerce.email_service.service;

import com.commerce.email_service.model.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public String sendEmail(EmailDTO emailDTO) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("shubhams7xyz@gmail.com");
            mailMessage.setTo(emailDTO.getTo());
            mailMessage.setSubject(emailDTO.getSubject());
            mailMessage.setText(emailDTO.getMessage());

            mailSender.send(mailMessage);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error while sending email: " + e.getMessage();
        }
    }
}
