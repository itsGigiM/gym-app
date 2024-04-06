package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.utils.UsernameGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TraineeServiceImpl;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplTests {

    @Mock
    private TraineesRepository traineesRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @InjectMocks
    private TraineeServiceImpl service;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        service = new TraineeServiceImpl(traineesRepository, usernameGenerator, 10, encoder);
    }
    @Test
    public void createTraineeAndSelectItsFirstName() {
        Trainee mockedTrainee = new Trainee("firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));
        Trainee selectedTrainee = service.selectTrainee(savedTrainee.getUserId());

        assertEquals("firstname", savedTrainee.getFirstName());
        assertEquals("firstname", selectedTrainee.getFirstName());
    }

    @Test
    public void selectValidTraineeByItsUsername() {
        Trainee mockedTrainee = new Trainee("firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.findByUsername(any())).thenReturn(Optional.of(mockedTrainee));
        Trainee selectedTrainee = service.selectTrainee(savedTrainee.getUsername());

        assertEquals("firstname", savedTrainee.getFirstName());
        assertEquals("firstname", selectedTrainee.getFirstName());
    }

    @Test
    public void selectInvalidTraineeByItsUsername_ThrowsException() {
        Trainee mockedTrainee = new Trainee("firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.selectTrainee(mockedTrainee.getUsername());
        });
    }

    @Test
    public void deleteUserUsingIdAndRetrieve_ThrowsException() {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));

        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));
        doNothing().when(traineesRepository).delete(any());

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        service.deleteTrainee(savedTrainee.getUserId());

        assertThrows(EntityNotFoundException.class, () -> {
            when(traineesRepository.findById(eq(savedTrainee.getUserId()))).thenReturn(Optional.empty());
            service.selectTrainee(savedTrainee.getUserId());
        });
    }

    @Test
    public void deleteUserUsingUsernameAndRetrieve_ThrowsException() {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));

        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findByUsername(any())).thenReturn(Optional.of(mockedTrainee));
        doNothing().when(traineesRepository).delete(any());

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        service.deleteTrainee("username");

        assertThrows(EntityNotFoundException.class, () -> {
            when(traineesRepository.findById(eq(savedTrainee.getUserId()))).thenReturn(Optional.empty());
            service.selectTrainee(savedTrainee.getUserId());
        });
    }

    @Test
    public void updateTraineesFirstName() {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        savedTrainee.setFirstName("Epam");
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.updateTrainee(savedTrainee.getUserId(), savedTrainee);

        assertEquals("Epam", service.selectTrainee(savedTrainee.getUserId()).
                getFirstName());
    }

    @Test
    public void updateNullTrainee_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateTrainee(10L, null);
        });
    }

    @Test
    public void changeTraineePassword() {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        savedTrainee.setPassword("epam");
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.changeTraineePassword(savedTrainee.getUserId(), "epam");

        assertEquals("epam", service.selectTrainee(savedTrainee.getUserId()).
                getPassword());
    }

    @Test
    public void changeTraineeActiveStatusToFalse() {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        savedTrainee.setActive(false);
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.activateDeactivateTrainee(savedTrainee.getUserId(), false);

        assertFalse(service.selectTrainee(savedTrainee.getUserId()).
                isActive());
    }

    @Test
    public void changeTrainersList() {
        Set<Trainer> trainerSet = new HashSet<>();
        trainerSet.add(new Trainer());
        trainerSet.add(new Trainer());
        trainerSet.add(new Trainer());
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        savedTrainee.setTrainers(trainerSet);
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.updateTrainersList(savedTrainee.getUserId(), trainerSet);

        assertEquals(3, service.selectTrainee(savedTrainee.getUserId()).
                getTrainers().size());
    }

    @Test
    public void getTraineeTrainingList() {
        String traineeUsername = "trainee";
        LocalDate fromDate = LocalDate.now().minusDays(7);
        LocalDate toDate = LocalDate.now();
        String trainerName = "trainer";

        Set<Training> trainings = new HashSet<>();

        when(traineesRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(new Trainee()));
        when(traineesRepository.findTraineeTrainings(traineeUsername, fromDate, toDate, trainerName, new TrainingType())).thenReturn(trainings);

        Set<Training> result = service.getTraineeTrainingList(traineeUsername, fromDate, toDate, trainerName, new TrainingType());

        assertEquals(trainings, result);
    }

    @Test
    public void getTraineeTrainingListTrainee_NotFound() {
        String traineeUsername = "nonExistingTrainee";
        LocalDate fromDate = LocalDate.now().minusDays(7);
        LocalDate toDate = LocalDate.now();
        String trainerName = "trainer";

        when(traineesRepository.findByUsername(traineeUsername)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                service.getTraineeTrainingList(traineeUsername, fromDate, toDate, trainerName, new TrainingType()));

    }

    @Test
    public void getUnassignedTrainers() {
        String traineeUsername = "traineeUsername";
        Set<Trainer> mockTrainers = new HashSet<>();

        when(traineesRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(new Trainee()));
        when(traineesRepository.findUnassignedTrainers(traineeUsername)).thenReturn(mockTrainers);

        Set<Trainer> result = service.getUnassignedTrainers(traineeUsername);

        assertEquals(mockTrainers, result);
    }
}
