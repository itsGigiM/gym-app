package com.example.taskspring.message;

import com.example.taskspring.dto.trainingDTO.TrainerSessionWorkHoursUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@NoArgsConstructor
@Slf4j
public class MessageProducer {
    private JmsTemplate jmsTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(TrainerSessionWorkHoursUpdateDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("DTO could not written as JSON {}", e.toString());
            return "{}";
        }
    }

    private String workHoursUpdateQueue;

    private String dlq;

    private int MAX_REDELIEVERIES;
    private AtomicInteger retryCount;

    @Autowired
    public MessageProducer(JmsTemplate jmsTemplate,
                           @Value("${work.hours.queue}") String workHoursUpdateQueue,
                           @Value("${spring.activemq.redelivery-policy.maximum-redeliveries}") int max_redelieveries,
                           @Value("${spring.activemq.dead-letter-queue-name}") String dlq) {
        this.jmsTemplate = jmsTemplate;
        objectMapper.registerModule(new JavaTimeModule());
        retryCount = new AtomicInteger(0);
        this.workHoursUpdateQueue = workHoursUpdateQueue;
        this.MAX_REDELIEVERIES = max_redelieveries;
        this.dlq = dlq;
    }

    public void sendWorkHoursUpdate(TrainerSessionWorkHoursUpdateDTO updateDTO, String token){
        String jsonUpdateDto = toJson(updateDTO);
        log.info("Sending message to the ActiveMQ broker server: {}", jsonUpdateDto);
        try {
            jmsTemplate.convertAndSend(workHoursUpdateQueue, jsonUpdateDto, dto -> {
                dto.setStringProperty("Authorization", token);
                return dto;
            });
        } catch (Exception ex) {
            log.error("Error sending message to ActiveMQ: {}", ex.getMessage());
            if (retryCount.incrementAndGet() > MAX_REDELIEVERIES) {
                retryCount.set(0);
                log.error("Retry unavaliable, sending message to DLQ");
                jmsTemplate.send(dlq, session -> session.createTextMessage(jsonUpdateDto));
            }else{
                retryCount.incrementAndGet();
                sendWorkHoursUpdate(updateDTO, token);
            }
        }
    }
}
