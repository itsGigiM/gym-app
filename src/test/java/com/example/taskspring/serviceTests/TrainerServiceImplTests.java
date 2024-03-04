package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.TrainersInMemoryDAO;
import com.example.taskspring.utils.UsernameGenerator;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TrainerServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTests {
    @InjectMocks
    private TrainerServiceImpl service;

    @Mock
    private TrainersInMemoryDAO trainersInMemoryDAO;

    @Mock
    private UsernameGenerator usernameGenerator;

    @BeforeEach
    public void setUp() {
        service = new TrainerServiceImpl(trainersInMemoryDAO, usernameGenerator, 10);
    }

    @Test
    public void CreateTrainerAndRetrieveIt() {
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "pass", true,  TrainingType.BOXING, 10L);
        when(usernameGenerator.generateUsername(anyString(), anyString())).thenReturn("Gigi.Mirziashvili");
        when(trainersInMemoryDAO.add(any())).thenReturn(trainer);
        when(trainersInMemoryDAO.get(any())).thenReturn(trainer);
        when(trainersInMemoryDAO.exists(any())).thenReturn(true);

        Long trainerId = service.createTrainer("Gigi", "Mirziashvili", true,
                TrainingType.BOXING);

        assertEquals("Gigi.Mirziashvili", service.selectTrainer(trainerId).getUsername());
    }

    @Test
    public void selectInvalidUser_ThrowsException() {
        assertThrows(NoSuchElementException.class, () -> service.selectTrainer(1033L));
    }

    @Test
    public void updateExistingTrainersUsername() {
        Trainer t = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "pass", true,  TrainingType.BOXING, 10L);
        when(trainersInMemoryDAO.add(any())).thenReturn(t);
        when(trainersInMemoryDAO.get(any())).thenReturn(t);
        when(trainersInMemoryDAO.exists(any())).thenReturn(true);
        t.setFirstName("Epam");
        when(trainersInMemoryDAO.set(any())).thenReturn(t);


        Long trainerId = service.createTrainer("Gigi", "Mirziashvili", true,
                TrainingType.BOXING);
        Trainer trainer = service.selectTrainer(trainerId);
        trainer.setFirstName("Epam");
        service.updateTrainer(trainerId, trainer);

        assertEquals(service.selectTrainer(trainerId).getFirstName(), "Epam");
    }

    @Test
    public void updateNameOfNonExistingTrainer_throwsException() {
        Trainer t = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "pass", true,  TrainingType.BOXING, 10L);
        when(trainersInMemoryDAO.add(any())).thenReturn(t);
        when(trainersInMemoryDAO.get(any())).thenReturn(t);
        when(trainersInMemoryDAO.exists(any())).thenReturn(true);

        Long trainerId = service.createTrainer("Gigi", "Mirziashvili", true,
                TrainingType.BOXING);
        Trainer trainer = service.selectTrainer(trainerId);
        trainer.setFirstName("Epam");
        assertThrows(IllegalArgumentException.class, () -> service.updateTrainer(trainerId, null));
    }
}
