package com.example.taskspring.message;

import com.example.taskspring.dto.trainingDTO.TrainerSessionWorkHoursUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducer {
    private final JmsTemplate jmsTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(TrainerSessionWorkHoursUpdateDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("DTO could not written as JSON {}", e.toString());
            return "{}";
        }
    }

    @Value("${work.hours.queue}")
    private String workHoursUpdateQueue;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void sendWorkHoursUpdate(TrainerSessionWorkHoursUpdateDTO updateDTO, String token){
        String jsonUpdateDto = toJson(updateDTO);
        log.info("Sending message to the ActiveMQ broker server: {}", jsonUpdateDto);
        jmsTemplate.convertAndSend(workHoursUpdateQueue, jsonUpdateDto, dto -> {
            dto.setStringProperty("Authorization", token);
            return dto;
        });
    }
}
