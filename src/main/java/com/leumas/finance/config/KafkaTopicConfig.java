package com.leumas.finance.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic transactionalCreateEventsTopic() {
        return new NewTopic("transactional-create-events", 3, (short) 1);
    }

    @Bean
    public NewTopic transactionalUpdateEventsTopic() {
        return new NewTopic("transactional-update-events", 3, (short) 1);
    }

    public NewTopic transactionalDeleteEventsTopic() {
        return new NewTopic("transactional-delete-events", 3, (short) 1);
    }
}
