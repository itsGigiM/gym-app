package com.example.taskspring.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DLQListener {

    @JmsListener(destination = "${spring.activemq.dead-letter-queue-name}")
    public void processDLQMessage(String message) {
        log.error("Message failed to process and was sent to the DLQ: {}", message);
    }
}
