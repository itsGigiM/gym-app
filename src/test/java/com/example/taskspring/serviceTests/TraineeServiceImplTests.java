package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.utils.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TraineeServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplTests {
    @InjectMocks
    private TraineeServiceImpl service;

    @Mock
    private TraineesInMemoryDAO traineesInMemoryDAO;

    @Mock
    private UsernameGenerator usernameGenerator;

    @BeforeEach
    public void setUp() {
        service = new TraineeServiceImpl(traineesInMemoryDAO, usernameGenerator, 10);
    }

    @Test
    public void CreateTraineeAndRetrieveIt() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "pass", true, 10L,  "Tbilisi", LocalDate.of(2022, 2, 2));
        when(usernameGenerator.generateUsername(anyString(), anyString())).thenReturn("Gigi.Mirziashvili");
        when(traineesInMemoryDAO.add(any())).thenReturn(trainee);
        when(traineesInMemoryDAO.get(any())).thenReturn(trainee);
        when(traineesInMemoryDAO.exists(any())).thenReturn(true);

        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));

        assertEquals("Gigi.Mirziashvili", service.selectTrainee(traineeId).getUsername());
    }

        @Test
        public void DeleteExistingTrainee() {
            Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                    "pass", true, 10L,  "Tbilisi", LocalDate.of(2022, 2, 2));
            when(traineesInMemoryDAO.add(any())).thenReturn(trainee);
            doNothing().when(traineesInMemoryDAO).remove(any());
            when(traineesInMemoryDAO.exists(any())).thenReturn(true);

            Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                    "Tbilisi", LocalDate.of(2022, 2, 2));
            service.deleteTrainee(traineeId);
            assertThrows(NoSuchElementException.class, () -> {
                when(traineesInMemoryDAO.get(eq(traineeId))).thenThrow(new NoSuchElementException());
                service.selectTrainee(traineeId);
            });
        }

    @Test
    public void UpdateNameOfExistingTrainee() {
        Trainee t = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "pass", true, 10L,  "Tbilisi", LocalDate.of(2022, 2, 2));
        when(traineesInMemoryDAO.add(any())).thenReturn(t);
        when(traineesInMemoryDAO.get(any())).thenReturn(t);
        when(traineesInMemoryDAO.exists(any())).thenReturn(true);
        t.setFirstName("Epam");
        when(traineesInMemoryDAO.set(any())).thenReturn(t);


        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(traineeId);
        trainee.setFirstName("Epam");
        service.updateTrainee(traineeId, trainee);

        assertEquals(service.selectTrainee(traineeId).getFirstName(), "Epam");
    }

    @Test
    public void UpdateNameOfNonExistingTrainee_throwsException() {
        Trainee t = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "pass", true, 10L,  "Tbilisi", LocalDate.of(2022, 2, 2));
        when(traineesInMemoryDAO.add(any())).thenReturn(t);
        when(traineesInMemoryDAO.get(any())).thenReturn(t);
        when(traineesInMemoryDAO.exists(any())).thenReturn(true);

        Long traineeId = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(traineeId);
        trainee.setFirstName("Epam");
        assertThrows(IllegalArgumentException.class, () -> service.updateTrainee(traineeId, null));
    }

    @Test
    public void selectInvalidUser_ThrowsException() {
        assertThrows(NoSuchElementException.class, () -> service.selectTrainee(1033L));
    }
}
