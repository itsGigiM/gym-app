package com.example.taskspring.MessageTests;
import com.example.taskspring.dto.trainingDTO.TrainerSessionWorkHoursUpdateDTO;
import com.example.taskspring.message.MessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageProducerTests {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private MessageProducer messageProducer;

    @BeforeEach
    void setUp() {
        messageProducer = new MessageProducer(jmsTemplate, "Workhours.queue", 3, "DLQ");
    }

    @Test
    void testSendWorkHoursSuccess() {
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO("john_doe", "John", "Doe", true, LocalDate.of(2002, 1, 1), 1L, "ADD");

        messageProducer.sendWorkHoursUpdate(updateDTO, "token");
    }


    @Test
    void testSendWorkHoursRetriesAndSendsToDLQ() {
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO("john_doe", "John", "Doe", true, LocalDate.of(2002, 1, 1), 1L, "ADD");
        willAnswer( invocation -> { throw new Exception("abc msg"); }).given(jmsTemplate).convertAndSend(anyString(), anyString(), any());
        messageProducer.sendWorkHoursUpdate(updateDTO, "token");
        verify(jmsTemplate, times(3)).convertAndSend(anyString(), anyString(), any());
        verify(jmsTemplate, times(1)).send(eq("DLQ"), any());
    }
}

