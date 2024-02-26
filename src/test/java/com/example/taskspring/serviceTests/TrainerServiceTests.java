package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.repository.TrainersInMemoryDAO;
import com.example.taskspring.utils.InMemoryStorage;
import com.example.taskspring.utils.UsernameGenerator;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TrainerService;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerServiceTests {
    private TrainerService service;

    @BeforeEach
    public void setUpService() {
        InMemoryStorage memo = new InMemoryStorage();
        service = new TrainerService(new TrainersInMemoryDAO(memo),
                new UsernameGenerator(new TraineesInMemoryDAO(memo), new TrainersInMemoryDAO(memo)));
    }    @Test
    public void testCreateAndSelect() {
        service.createTrainer("Gigi", "Mirziashvili", true, 1033L,
                "BOXING");
        assertEquals(service.selectTrainer(1033L).getUsername(), "Gigi.Mirziashvili");
    }

    @Test
    public void testUpdate() {
        service.createTrainer("Gigi", "Mirziashvili", true, 1033L,
                "BOXING");
        Trainer trainer = service.selectTrainer(1033L);
        trainer.setFirstName("Epam");
        service.updateTrainer(1033L, trainer);
        assertEquals(service.selectTrainer(1033L).getFirstName(), "Epam");
    }
}
