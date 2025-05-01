package com.leumas.finance.kafka.producer;

import com.leumas.finance.kafka.event.TransactionalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatisticsProducer {

    private final KafkaTemplate<String, TransactionalEvent> kafkaTemplate;

    private static final String TOPIC = "transactional-events";

    public void sendEvent(TransactionalEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
