package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.repository.TrainersInMemoryDAO;
import com.example.taskspring.repository.InMemoryStorage;
import com.example.taskspring.utils.UsernameGenerator;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TrainerService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerServiceTests {
    private TrainerService service;

    @BeforeEach
    public void setUpService() {
        InMemoryStorage memo = new InMemoryStorage();
        service = new TrainerService(new TrainersInMemoryDAO(memo),
                new UsernameGenerator(new TraineesInMemoryDAO(memo), new TrainersInMemoryDAO(memo)), 10);
    }    @Test
    public void testCreateAndSelect() {
        Long trainerId = service.createTrainer("Gigi", "Mirziashvili", true,
                TrainingType.BOXING);
        assertEquals(service.selectTrainer(trainerId).getUsername(), "Gigi.Mirziashvili");
    }

    @Test
    public void testSelectInvalidUser() {
        assertThrows(NoSuchElementException.class, () -> service.selectTrainer(1033L));
    }

    @Test
    public void testUpdate() {
        Long trainerId = service.createTrainer("Gigi", "Mirziashvili", true,
                TrainingType.BOXING);
        Trainer trainer = service.selectTrainer(trainerId);
        trainer.setFirstName("Epam");
        service.updateTrainer(trainerId, trainer);
        assertEquals(service.selectTrainer(trainerId).getFirstName(), "Epam");
    }

    @Test
    public void testUpdateNullTrainee() {
        Long trainerId = service.createTrainer("Gigi", "Mirziashvili", true,
                TrainingType.BOXING);
        Trainer trainer = service.selectTrainer(trainerId);
        trainer.setFirstName("Epam");
        assertThrows(IllegalArgumentException.class, () -> service.updateTrainer(trainerId, null));
    }
}
