package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainer;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TrainerService;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerServiceTests {
    private TrainerService service;

    @BeforeEach
    public void setUpService() {
        service = new TrainerService();
    }
    @Test
    public void testCreateAndSelect() {
        service.createTrainer("Gigi", "Mirziashvili", true, "1033",
                "BOXING");
        assertEquals(service.selectTrainer("1033").getUsername(), "Gigi.Mirziashvili");
    }

    @Test
    public void testUpdate() {
        service.createTrainer("Gigi", "Mirziashvili", true, "1033",
                "BOXING");
        Trainer trainer = service.selectTrainer("1033");
        trainer.setFirstName("Epam");
        service.updateTrainer("1033", trainer);
        assertEquals(service.selectTrainer("1033").getFirstName(), "Epam");
    }
}
