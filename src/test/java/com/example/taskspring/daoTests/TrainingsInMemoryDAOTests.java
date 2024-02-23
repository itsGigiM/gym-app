package com.example.taskspring;

import model.*;
import org.junit.jupiter.api.*;
import repository.TrainersInMemoryDAO;
import repository.TrainingsDAO;
import repository.TrainingsInMemoryDAO;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingsInMemoryDAOTests {
    private TrainingsInMemoryDAO repo;

    @BeforeEach
    public void setUpRepository() {
        repo = new TrainingsInMemoryDAO();
    }
    @Test
    public void testAddAndGet() {
        Training training = new Training("1033", "10", "12",
                "Box", TrainingType.BOXING, new Date(), Duration.ofHours(2));
        repo.add(training);

        assertTrue(repo.exists("1033"));
        assertEquals(training, repo.get("1033"));
    }

    @Test
    public void testGetAll() {
        Training training1 = new Training("1034", "10", "12",
                "Box", TrainingType.BOXING, new Date(), Duration.ofHours(2));
        repo.add(training1);

        Training training2 = new Training("1035", "10", "12",
                "Box", TrainingType.BOXING, new Date(), Duration.ofHours(2));
        repo.add(training2);

        List<Training> l = repo.getAll();

        assertTrue(l.contains(training1));
        assertTrue(l.contains(training2));
        assertEquals(l.size(), 2);

    }

    @Test
    public void testToString() {
        assertSame("{}", repo.toString());

        Training training = new Training("1033", "10", "12",
                "Box", TrainingType.BOXING, new Date(2024, 1, 1), Duration.ofHours(2));
        repo.add(training);

        assertEquals("{1033=Training(trainingId=1033, traineeId=10, trainerId=12, trainingName=Box, trainingType=BOXING, trainingDate=Fri Feb 01 00:00:00 GET 3924, duration=PT2H)}",
                repo.toString());
    }
}
