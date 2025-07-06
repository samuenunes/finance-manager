package com.leumas.finance.kafka.producer;

import com.leumas.finance.kafka.event.TransactionalEvent;
import com.leumas.finance.kafka.event.TransactionalUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatisticsProducer {

    private final KafkaTemplate<String, TransactionalEvent> kafkaTemplateCreate;
    private final KafkaTemplate<String, TransactionalUpdateEvent> kafkaTemplateUpdate;

    private static final String TOPIC_CREATE = "transactional-create-events";
    private static final String TOPIC_UPDATE = "transactional-update-events";
    private static final String TOPIC_DELETE = "transactional-delete-events";


    public void sendCreateEvent(TransactionalEvent event) {
        kafkaTemplateCreate.send(TOPIC_CREATE, event);
    }

    public void sendUpdateEvent(TransactionalUpdateEvent event) {
        kafkaTemplateUpdate.send(TOPIC_UPDATE, event);
    }

    public void sendDeleteEvent(TransactionalEvent event) {
        kafkaTemplateCreate.send(TOPIC_DELETE, event);
    }
}
