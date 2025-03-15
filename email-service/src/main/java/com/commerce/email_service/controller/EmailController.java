package com.commerce.email_service.controller;

import com.commerce.email_service.model.EmailDTO;
import com.commerce.email_service.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody String emailDTO) throws JsonProcessingException {
        emailService.sendEmail(emailDTO);
        return "Success";
    }

}
