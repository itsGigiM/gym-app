package com.example.taskspring.serviceTests;

import com.example.taskspring.model.*;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.repository.TrainingsRepository;
import com.example.taskspring.repository.UsersRepository;
import com.example.taskspring.service.TrainingServiceImpl;
import com.example.taskspring.utils.Authenticator;
import jakarta.persistence.EntityNotFoundException;
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
    private TraineesRepository traineesRepository;

    @Mock
    private TrainersRepository trainersRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private Authenticator authenticator;

    @BeforeEach
    public void setUp() {
        service = new TrainingServiceImpl(repository, traineesRepository,
                trainersRepository, authenticator, usersRepository);
    }
    @Test
    public void createTrainingAndSelectItsFirstName() throws AuthenticationException {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        User user = new User("g", "m", "u", "p", true);
        Trainer trainer = new Trainer("g", "m", "u", "p", true, trainingType);
        Trainee trainee = new Trainee("g", "m", "u", "p", true, "t", LocalDate.of(2000, 1, 1));
        Training mockedTraining = new Training(trainee, trainer, "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1));
        when(repository.save(any(Training.class))).thenReturn(mockedTraining);
        when(usersRepository.findById(any())).thenReturn(Optional.of(user));
        when(trainersRepository.findByUserUsername(any())).thenReturn(Optional.of(trainer));
        doNothing().when(authenticator).authenticate(anyString(), anyString());

        Training savedTraining = service.createTraining(trainee, trainer, "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1), "admin.admin", "password");
        when(repository.findById(any())).thenReturn(Optional.of(mockedTraining));
        Training selectedTrainer = service.selectTraining(savedTraining.getTrainingId(), "admin.admin", "password");

        assertEquals("boxing", selectedTrainer.getTrainingName());
    }

    @Test
    public void selectNonExistingTraining_ThrowsException() throws AuthenticationException {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        User user = new User("g", "m", "u", "p", true);
        Training mockedTraining = new Training(new Trainee(), new Trainer(), "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1));
        doNothing().when(authenticator).authenticate(anyString(), anyString());

        assertThrows(NoSuchElementException.class, () -> {
            service.selectTraining(10L,"admin.admin", "password");
        });
    }

    @Test
    public void createTrainingWithInvalidTrainerA_throws_Exception() throws AuthenticationException {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);

        assertThrows(IllegalArgumentException.class, () -> {
            service.createTraining(new Trainee(), new Trainer(), "boxing", trainingType,
                    LocalDate.of(2000, 1, 1), Duration.ofHours(1), "admin.admin", "password");
        });
    }

}
