package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.utils.UsernameGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TraineeServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplTests {

    @Mock
    private TraineesRepository traineesRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private Authenticator authenticator;

    @InjectMocks
    private TraineeServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new TraineeServiceImpl(traineesRepository, usernameGenerator, authenticator, 10);
    }
    @Test
    public void CreateTraineeAndSelectItsFirstName() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee("firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));
        Trainee selectedTrainee = service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password");

        assertEquals("firstname", savedTrainee.getFirstName());
        assertEquals("firstname", selectedTrainee.getFirstName());
    }

    @Test
    public void DeleteUserUsingIdAndRetrieve_ThrowsException() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));

        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));
        doNothing().when(traineesRepository).delete(any());

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        service.deleteTrainee(savedTrainee.getUserId(), "admin.admin", "password");

        assertThrows(EntityNotFoundException.class, () -> {
            when(traineesRepository.findById(eq(savedTrainee.getUserId()))).thenReturn(Optional.empty());
            service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password");
        });
    }

    @Test
    public void DeleteUserUsingUsernameAndRetrieve_ThrowsException() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));

        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findByUsername(any())).thenReturn(Optional.of(mockedTrainee));
        doNothing().when(traineesRepository).delete(any());

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        service.deleteTrainee("username", "admin.admin", "password");

        assertThrows(EntityNotFoundException.class, () -> {
            when(traineesRepository.findById(eq(savedTrainee.getUserId()))).thenReturn(Optional.empty());
            service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password");
        });
    }

    @Test
    public void UpdateTraineesFirstName() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        savedTrainee.setFirstName("Epam");
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.updateTrainee(savedTrainee.getUserId(), savedTrainee, "admin.admin", "password");

        assertEquals("Epam", service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password").
                getFirstName());
    }

    @Test
    public void UpdateNullTrainee_ThrowsException() throws AuthenticationException {
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateTrainee(10L, null, "admin.admin", "password");
        });
    }

    @Test
    public void changeTraineePassword() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        savedTrainee.setPassword("epam");
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.changeTraineePassword(savedTrainee.getUserId(), "epam", "admin.admin", "password");

        assertEquals("epam", service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password").
                getPassword());
    }

    @Test
    public void changeTraineeActiveStatusToFalse() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));

        savedTrainee.setActive(false);
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.activateDeactivateTrainee(savedTrainee.getUserId(), false, "admin.admin", "password");

        assertFalse(service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password").
                isActive());
    }

    @Test
    public void changeTrainersList() throws AuthenticationException {
        Set<Trainer> trainerSet = new HashSet<>();
        trainerSet.add(new Trainer());
        trainerSet.add(new Trainer());
        trainerSet.add(new Trainer());
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        savedTrainee.setTrainers(trainerSet);
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.updateTrainersList(savedTrainee.getUserId(), trainerSet, "admin.admin", "password");

        assertEquals(3, service.selectTrainee(savedTrainee.getUserId(), "admin.admin", "password").
                getTrainers().size());
    }
}
