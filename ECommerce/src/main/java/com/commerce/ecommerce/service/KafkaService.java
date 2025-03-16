package com.commerce.ecommerce.service;

import com.commerce.ecommerce.feignClient.EmailFeign;
import com.commerce.ecommerce.model.dto.EmailDTO;
import com.commerce.ecommerce.model.entity.Order;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, EmailDTO> emailKafkaTemplate;

    @Autowired
    EmailFeign emailFeign;


    public boolean sendMessage(String message) {
        //kafkaTemplate.send("read-topic", "Hey, received message from BuyBliss \n " + message);
        kafkaTemplate.send("abc-topic", "Hey, received message from BuyBliss \n " + message);
        return true;
    }

    public void sendEmail(Order order) {
        EmailDTO emailDTO = EmailDTO.builder().to(order.getConsumer().getEmail())
                .subject("BUY BLISS: Your Order #" + order.getOrderId() + " is Confirmed")
                .message("""
                        Thanks for choosing Buy Bliss! You can track your order on our Website.
                        
                        For any queries, reach out to your assigned customer service executive.
                        Shubham S
                        Contact No. +91 7304140456
                        
                        
                        Buy Bliss :) ..here money can buy happiness!""")
                .build();

        try {
            emailKafkaTemplate.send("email-topic", emailDTO);
            log.info("Successfully sent using kafka");                                  ///bug here in scenario: if kafka goes down in between, here not throwing an exception
        } catch(Exception kafkaEx) {
            try {
                log.error("Failed to publish on kafka email-topic, sending email via feign client! Error: ", kafkaEx);
                emailFeign.sendEmail(emailDTO);
                log.info("Successfully sent using feign");
            }
            catch (FeignException e) {
                log.error("Email Service Feign failed ", e);
            }
        }
    }
}
