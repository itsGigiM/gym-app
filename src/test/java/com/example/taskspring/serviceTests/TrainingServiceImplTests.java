package com.example.taskspring.serviceTests;

import com.example.taskspring.model.*;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.TrainingsRepository;
import com.example.taskspring.service.TrainingServiceImpl;
import com.example.taskspring.utils.Authenticator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTests {
    @InjectMocks
    private TrainingServiceImpl service;

    @Mock
    private TrainingsRepository repository;

    @Mock
    private TrainersRepository trainersRepository;

    @Mock
    private Authenticator authenticator;

    @BeforeEach
    public void setUp() {
        service = new TrainingServiceImpl(repository, trainersRepository, authenticator);
    }
    @Test
    public void createTrainingAndSelectItsFirstName() throws AuthenticationException {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        Trainer trainer = new Trainer("g", "m", "u", "p", true, trainingType);
        Trainee trainee = new Trainee("g", "m", "u", "p", true, "t", LocalDate.of(2000, 1, 1));
        Training mockedTraining = new Training(trainee, trainer, "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1));
        when(repository.save(any(Training.class))).thenReturn(mockedTraining);
        when(trainersRepository.findByUsername(any())).thenReturn(Optional.of(trainer));
        doNothing().when(authenticator).authenticate(anyString(), anyString());

        Training savedTraining = service.createTraining(trainee, trainer, "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1), "admin.admin", "password");
        when(repository.findById(any())).thenReturn(Optional.of(mockedTraining));
        Training selectedTrainer = service.selectTraining(savedTraining.getTrainingId(), "admin.admin", "password");

        assertEquals("boxing", selectedTrainer.getTrainingName());
    }

    @Test
    public void selectNonExistingTraining_ThrowsException() throws AuthenticationException {
        doNothing().when(authenticator).authenticate(anyString(), anyString());

        assertThrows(NoSuchElementException.class, () -> {
            service.selectTraining(10L,"admin.admin", "password");
        });
    }

    @Test
    public void createTrainingWithInvalidTrainerA_throws_Exception() {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);

        assertThrows(IllegalArgumentException.class, () -> {
            service.createTraining(new Trainee(), new Trainer(), "boxing", trainingType,
                    LocalDate.of(2000, 1, 1), Duration.ofHours(1), "admin.admin", "password");
        });
    }

}
