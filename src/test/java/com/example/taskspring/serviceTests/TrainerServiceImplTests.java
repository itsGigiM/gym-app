package com.example.taskspring.serviceTests;

import com.example.taskspring.model.*;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.service.TrainerServiceImpl;
import com.example.taskspring.utils.UsernameGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTests {

    @Mock
    private TrainersRepository trainersRepository;
    
    @Mock
    private UsernameGenerator usernameGenerator;
    
    @InjectMocks
    private TrainerServiceImpl service;

    @Mock
    private PasswordEncoder encoder;

    private final TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);

    @BeforeEach
    public void setUp() {
        service = new TrainerServiceImpl(usernameGenerator, trainersRepository, 10, encoder);
    }
    @Test
    public void createTrainerAndSelectItsFirstName() {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));
        Trainer selectedTrainer = service.selectTrainer(savedTrainer.getUserId());

        assertEquals("firstname", savedTrainer.getFirstName());
        assertEquals("firstname", selectedTrainer.getFirstName());
    }

    @Test
    public void selectTrainerByItsUsername() {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);
        when(trainersRepository.findByUsername(any())).thenReturn(Optional.of(mockedTrainer));
        Trainer selectedTrainer = service.selectTrainer(savedTrainer.getUsername());

        assertEquals("firstname", savedTrainer.getFirstName());
        assertEquals("firstname", selectedTrainer.getFirstName());
    }

    @Test
    public void selectInvalidTrainerByItsUsername_ThrowsException() {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.selectTrainer(mockedTrainer.getUsername());
        });
    }

    @Test
    public void updateTrainersFirstName() {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);

        savedTrainer.setFirstName("Epam");
        when(trainersRepository.save(any(Trainer.class))).thenReturn(savedTrainer);
        when(trainersRepository.findById(any())).thenReturn(Optional.of(savedTrainer));
        service.updateTrainer(savedTrainer.getUserId(), savedTrainer);

        assertEquals("Epam", service.selectTrainer(savedTrainer.getUserId()).
                getFirstName());
    }

    @Test
    public void updateNullTrainer_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateTrainer(10L, null);
        });
    }

    @Test
    public void updateNonExistingTrainer_ThrowsException() {
        assertThrows(EntityNotFoundException.class, () -> {
            service.updateTrainer(10L, new Trainer("f", "s", "u", "p", true, trainingType));
        });
    }

    @Test
    public void changeTrainerPassword() {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);

        savedTrainer.setPassword("epam");
        when(trainersRepository.save(any(Trainer.class))).thenReturn(savedTrainer);
        service.changeTrainerPassword(savedTrainer.getUserId(), "epam");

        assertEquals("epam", service.selectTrainer(savedTrainer.getUserId()).
                getPassword());
    }

    @Test
    public void changeTrainerActiveStatusToFalse() {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);

        savedTrainer.setActive(false);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(savedTrainer);
        service.activateDeactivateTrainer(savedTrainer.getUserId(), false);

        assertFalse(service.selectTrainer(savedTrainer.getUserId()).
                isActive());
    }

    @Test
    public void testGetTrainerTrainingList() {
        String trainerUsername = "trainer";
        LocalDate fromDate = LocalDate.now().minusDays(7);
        LocalDate toDate = LocalDate.now();

        Set<Training> trainings = new HashSet<>();

        when(trainersRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(new Trainer()));
        when(trainersRepository.findTrainerTrainings(trainerUsername, fromDate, toDate, "trainee")).thenReturn(trainings);

        Set<Training> result = service.getTrainerTrainingList(trainerUsername, fromDate, toDate, "trainee");

        assertEquals(trainings, result);

    }
}
