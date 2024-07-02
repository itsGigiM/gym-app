package com.example.taskspring;

import lombok.Data;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Data
public class MessageListener {

    private String receivedMessage;
    private CountDownLatch latch = new CountDownLatch(1);

    @JmsListener(destination = "Workhours.queue")
    public void receiveMessage(String message) {
        this.receivedMessage = message;
        latch.countDown();
    }

}
