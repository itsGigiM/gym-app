package com.example.taskspring.serviceTests;

import com.example.taskspring.model.*;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.service.TrainerServiceImpl;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.utils.UsernameGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTests {

    @Mock
    private TrainersRepository trainersRepository;
    
    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private Authenticator authenticator;

    @InjectMocks
    private TrainerServiceImpl service;

    private final TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);

    @BeforeEach
    public void setUp() {
        service = new TrainerServiceImpl(usernameGenerator, trainersRepository, authenticator, 10);
    }
    @Test
    public void CreateTrainerAndSelectItsFirstName() throws AuthenticationException {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);
        doNothing().when(authenticator).authenticate(anyString(), anyString());

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));
        Trainer selectedTrainer = service.selectTrainer(savedTrainer.getUserId(), "admin.admin", "password");

        assertEquals("firstname", savedTrainer.getFirstName());
        assertEquals("firstname", selectedTrainer.getFirstName());
    }

    @Test
    public void UpdateTrainersFirstName() throws AuthenticationException {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);

        savedTrainer.setFirstName("Epam");
        when(trainersRepository.save(any(Trainer.class))).thenReturn(savedTrainer);
        service.updateTrainer(savedTrainer.getUserId(), savedTrainer, "admin.admin", "password");

        assertEquals("Epam", service.selectTrainer(savedTrainer.getUserId(), "admin.admin", "password").
                getFirstName());
    }

    @Test
    public void UpdateNullTrainer_ThrowsException() throws AuthenticationException {
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateTrainer(10L, null, "admin.admin", "password");
        });
    }

    @Test
    public void UpdateNonExistingTrainer_ThrowsException() throws AuthenticationException {
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        assertThrows(EntityNotFoundException.class, () -> {
            service.updateTrainer(10L, new Trainer("f", "s", "u", "p", true, trainingType), "admin.admin", "password");
        });
    }

    @Test
    public void changeTrainerPassword() throws AuthenticationException {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);

        savedTrainer.setPassword("epam");
        when(trainersRepository.save(any(Trainer.class))).thenReturn(savedTrainer);
        service.changeTrainerPassword(savedTrainer.getUserId(), "epam", "admin.admin", "password");

        assertEquals("epam", service.selectTrainer(savedTrainer.getUserId(), "admin.admin", "password").
                getPassword());
    }

    @Test
    public void changeTrainerActiveStatusToFalse() throws AuthenticationException {
        Trainer mockedTrainer = new Trainer("firstname", "lastname", "username", "password",
                true, trainingType);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(mockedTrainer);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(trainersRepository.findById(any())).thenReturn(Optional.of(mockedTrainer));

        Trainer savedTrainer = service.createTrainer("firstname", "lastname", true,
                trainingType);

        savedTrainer.setActive(false);
        when(trainersRepository.save(any(Trainer.class))).thenReturn(savedTrainer);
        service.activateDeactivateTrainer(savedTrainer.getUserId(), false, "admin.admin", "password");

        assertFalse(service.selectTrainer(savedTrainer.getUserId(), "admin.admin", "password").
                isActive());
    }
}
