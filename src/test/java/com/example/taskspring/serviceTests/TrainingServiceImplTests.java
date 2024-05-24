package com.example.taskspring.serviceTests;

import com.example.taskspring.config.FeignClientInterceptor;
import com.example.taskspring.interfaces.DurationServiceInterface;
import com.example.taskspring.model.*;
import com.example.taskspring.repository.repositories.*;
import com.example.taskspring.service.TrainingServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    private TraineesRepository traineesRepository;

    @Mock
    private DurationServiceInterface restTemplate;

    @Mock
    private FeignClientInterceptor feignClientInterceptor;

    @BeforeEach
    public void setUp() {
        service = new TrainingServiceImpl(repository, trainersRepository, traineesRepository, restTemplate,
                feignClientInterceptor);
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
        when(traineesRepository.findByUsername(any())).thenReturn(Optional.of(trainee));

        Training savedTraining = service.createTraining(trainee, trainer, "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1));
        when(repository.findById(any())).thenReturn(Optional.of(mockedTraining));
        Training selectedTrainer = service.selectTraining(savedTraining.getTrainingId());

        assertEquals("boxing", selectedTrainer.getTrainingName());
    }

    @Test
    public void selectNonExistingTraining_ThrowsException() {
        assertThrows(NoSuchElementException.class, () -> {
            service.selectTraining(10L);
        });
    }

    @Test
    public void createTrainingWithInvalidTrainerA_throws_Exception() {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);

        assertThrows(IllegalArgumentException.class, () -> {
            service.createTraining(new Trainee(), new Trainer(), "boxing", trainingType,
                    LocalDate.of(2000, 1, 1), Duration.ofHours(1));
        });
    }

}
