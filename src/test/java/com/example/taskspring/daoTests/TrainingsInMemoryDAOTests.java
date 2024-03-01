package com.example.taskspring.daoTests;

import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.InMemoryStorage;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import org.junit.jupiter.api.*;
import com.example.taskspring.repository.TrainingsInMemoryDAO;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingsInMemoryDAOTests {
    private TrainingsInMemoryDAO repo;

    @BeforeEach
    public void setUpRepository() {
        InMemoryStorage memo = new InMemoryStorage();
        repo = new TrainingsInMemoryDAO(memo);
    }
    @Test
    public void testAddAndGet() {
        Training training = new Training(1033L, 10L, 12L,
                "Box", TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofHours(2));
        repo.add(training);

        assertTrue(repo.exists(1033L));
        assertEquals(training, repo.get(1033L));
    }

    @Test
    public void testGetAll() {
        Training training1 = new Training(1034L, 10L, 12L,
                "Box", TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofHours(2));
        repo.add(training1);

        Training training2 = new Training(1035L, 10L, 12L,
                "Box", TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofHours(2));
        repo.add(training2);

        List<Training> l = repo.getAll();

        assertTrue(l.contains(training1));
        assertTrue(l.contains(training2));
        assertEquals(l.size(), 2);

    }

    @Test
    public void testToString() {
        assertSame("{}", repo.toString());

        Training training = new Training(1033L, 10L, 12L,
                "Box", TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofHours(2));
        repo.add(training);

        assertEquals("{1033=Training(trainingId=1033, traineeId=10, trainerId=12, trainingName=Box, trainingType=BOXING, trainingDate=2022-02-02, duration=PT2H)}",
                repo.toString());
    }
}
