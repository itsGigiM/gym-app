package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import org.junit.jupiter.api.*;
import com.example.taskspring.repository.TrainingsInMemoryDAO;
import com.example.taskspring.service.TrainingServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTests {
    @InjectMocks
    private TrainingServiceImpl service;

    @Mock
    private TrainingsInMemoryDAO trainingsInMemoryDAO;
    
    @Test
    public void createTrainingAndRetrieveIt() {
        Training t = new Training(10L, 11L, 12L, "Boxing",
                TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofHours(1));
        when(trainingsInMemoryDAO.add(any())).thenReturn(t);
        when(trainingsInMemoryDAO.get(any())).thenReturn(t);
        when(trainingsInMemoryDAO.exists(any())).thenReturn(true);

        service.createTraining(10L, 11L, 12L, "Boxing",
                TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofHours(1));

        assertEquals("Boxing", service.selectTraining(10L).getTrainingName());
    }

    @Test
    public void selectInvalidTraining_ThrowsException() {
        assertThrows(NoSuchElementException.class, () -> service.selectTraining(1033L));
    }


}
