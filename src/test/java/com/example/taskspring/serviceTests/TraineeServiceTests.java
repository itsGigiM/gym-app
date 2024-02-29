package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.repository.TrainersInMemoryDAO;
import com.example.taskspring.repository.InMemoryStorage;
import com.example.taskspring.utils.UsernameGenerator;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TraineeService;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
public class TraineeServiceTests {
    private TraineeService service;

    @BeforeEach
    public void setUpService() {
        InMemoryStorage memo = new InMemoryStorage();
        service = new TraineeService(new TraineesInMemoryDAO(memo),
                new UsernameGenerator(new TraineesInMemoryDAO(memo), new TrainersInMemoryDAO(memo)));
    }
    @Test
    public void testCreateAndSelect() {
        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        assertEquals(service.selectTrainee(1033L).getUsername(), "Gigi.Mirziashvili");
    }

    @Test
    public void testDelete() {
        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        service.deleteTrainee(1033L);
        assertThrows(NoSuchElementException.class, () -> {
            service.selectTrainee(1033L);
        });
    }

    @Test
    public void testUpdate() {
        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(1033L);
        trainee.setFirstName("Epam");
        service.updateTrainee(1033L, trainee);
        assertEquals(service.selectTrainee(1033L).getFirstName(), "Epam");
    }

    @Test
    public void testUpdateNullTrainee() {
        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(1033L);
        trainee.setFirstName("Epam");
        assertThrows(IllegalArgumentException.class, () -> service.updateTrainee(1033L, null));
    }
}
