package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import org.junit.jupiter.api.*;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.service.TraineeService;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeServiceTests {
    private TraineeService service;

    @BeforeEach
    public void setUpService() {
        service = new TraineeService();
    }
    @Test
    public void testCreateAndSelect() {
        service.createTrainee("Gigi", "Mirziashvili", true, "1033",
                "Tbilisi", LocalDate.of(2022, 2, 2));
        assertEquals(service.selectTrainee("1033").getUsername(), "Gigi.Mirziashvili");
    }

    @Test
    public void testDelete() {
        service.createTrainee("Gigi", "Mirziashvili", true, "1033",
                "Tbilisi", LocalDate.of(2022, 2, 2));
        service.deleteTrainee("1033");
        assertThrows(NoSuchElementException.class, () -> {
            service.selectTrainee("1033");
        });
    }

    @Test
    public void testUpdate() {
        service.createTrainee("Gigi", "Mirziashvili", true, "1033",
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee("1033");
        trainee.setFirstName("Epam");
        service.updateTrainee("1033", trainee);
        assertEquals(service.selectTrainee("1033").getFirstName(), "Epam");
    }
}
