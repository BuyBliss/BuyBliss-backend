package com.commerce.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaConfig {          // no role      //automatically new topics getting created by producer kafkaTemplate
                                    // might use this class in future cases
    /*@Bean
    public NewTopic topic() {
        return TopicBuilder
                .name("message-topic")
                .build();
    }*/
}
