package com.example.taskspring.serviceTests;

import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.InMemoryStorage;
import org.junit.jupiter.api.*;
import com.example.taskspring.repository.TrainingsInMemoryDAO;
import com.example.taskspring.service.TrainingService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingServiceTests {
    private TrainingService service;

    @BeforeEach
    public void setUpService() {
        InMemoryStorage memo = new InMemoryStorage();
        TrainingsInMemoryDAO repo = new TrainingsInMemoryDAO(memo);
        service = new TrainingService(repo);
    }
    @Test
    public void testCreateAndSelect() {
        service.createTraining(10L,1032L, 1033L, "BOXING", TrainingType.BOXING,
                LocalDate.of(2022, 2, 2), Duration.ofHours(1));
        assertEquals(service.selectTraining(10L).getTrainingName(), "BOXING");
    }

    @Test
    public void testSelectInvalidTraining() {
        assertThrows(NoSuchElementException.class, () -> service.selectTraining(1033L));
    }


}
