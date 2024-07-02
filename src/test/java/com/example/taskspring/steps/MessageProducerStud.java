package com.example.taskspring.steps;

import com.example.taskspring.dto.trainingDTO.TrainerSessionWorkHoursUpdateDTO;
import com.example.taskspring.message.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class MessageProducerStud extends MessageProducer {
    public void sendWorkHoursUpdate(TrainerSessionWorkHoursUpdateDTO updateDTO, String token){
        return;
    }
}
