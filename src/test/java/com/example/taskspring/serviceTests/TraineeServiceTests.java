package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.repository.TrainersInMemoryDAO;
import com.example.taskspring.repository.InMemoryStorage;
import com.example.taskspring.utils.UsernameGenerator;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TraineeService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
public class TraineeServiceTests {
    private TraineeService service;

    @BeforeEach
    public void setUpService() {
        InMemoryStorage memo = new InMemoryStorage();
        service = new TraineeService(new TraineesInMemoryDAO(memo),
                new UsernameGenerator(new TraineesInMemoryDAO(memo), new TrainersInMemoryDAO(memo)), 10);
    }
    @Test
    public void CreateTraineeAndRetrieveIt() {
        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        assertEquals(service.selectTrainee(traineeId).getUsername(), "Gigi.Mirziashvili");
    }

    @Test
    public void DeleteExistingTrainee() {
        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        service.deleteTrainee(traineeId);
        assertThrows(NoSuchElementException.class, () -> {
            service.selectTrainee(1033L);
        });
    }

    @Test
    public void UpdateNameOfExistingTrainee() {
        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(traineeId);
        trainee.setFirstName("Epam");
        service.updateTrainee(traineeId, trainee);
        assertEquals(service.selectTrainee(traineeId).getFirstName(), "Epam");
    }

    @Test
    public void UpdateNameOfNonExistingTrainee_throwsException() {
        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(traineeId);
        trainee.setFirstName("Epam");
        assertThrows(IllegalArgumentException.class, () -> service.updateTrainee(traineeId, null));
    }
}
