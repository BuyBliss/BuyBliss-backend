package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.dto.EmailDTO;
import com.commerce.ecommerce.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, EmailDTO> emailKafkaTemplate;


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

        emailKafkaTemplate.send("email-topic", emailDTO);
    }
}
