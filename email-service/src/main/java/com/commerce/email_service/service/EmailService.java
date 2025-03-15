package com.commerce.email_service.service;

import com.commerce.email_service.model.EmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    @KafkaListener(topics = "email-topic", groupId = "my-group")
    public void sendEmail(String email) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        EmailDTO emailDTO = objectMapper.readValue(email, EmailDTO.class);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("shubhams7xyz@gmail.com");
        mailMessage.setTo(emailDTO.getTo());
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setText(emailDTO.getMessage());

        mailSender.send(mailMessage);
    }

    /// for testing purpose
    //@KafkaListener(topics = "abc-topic", groupId = "my-group")
    public void printMessage(String message) {
        System.out.println(message);
    }
}
