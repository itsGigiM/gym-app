package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.UsersRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplTests {

    @Mock
    private TraineesRepository traineesRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private Authenticator authenticator;

    @InjectMocks
    private TraineeServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new TraineeServiceImpl(traineesRepository, usernameGenerator, authenticator, 10, usersRepository);
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
        Trainee selectedTrainee = service.selectTrainee(savedTrainee.getTraineeId(), "admin.admin", "password");

        assertEquals("firstname", savedTrainee.getUser().getFirstName());
        assertEquals("firstname", selectedTrainee.getUser().getFirstName());
    }

    @Test
    public void DeleteUserAndRetrieve_ThrowsException() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee(10L, "firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(any(Trainee.class))).thenReturn(mockedTrainee);
        doNothing().when(authenticator).authenticate(anyString(), anyString());
        when(traineesRepository.findById(any())).thenReturn(Optional.of(mockedTrainee));
        doNothing().when(traineesRepository).delete(any());

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        service.deleteTrainee(savedTrainee.getTraineeId(), "admin.admin", "password");

        assertThrows(EntityNotFoundException.class, () -> {
            when(traineesRepository.findById(eq(savedTrainee.getTraineeId()))).thenThrow(new EntityNotFoundException());
            service.selectTrainee(savedTrainee.getTraineeId(), "admin.admin", "password");
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

        savedTrainee.getUser().setFirstName("Epam");
        when(traineesRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
        service.updateTrainee(savedTrainee.getTraineeId(), savedTrainee, "admin.admin", "password");

        assertEquals("Epam", service.selectTrainee(savedTrainee.getTraineeId(), "admin.admin", "password").
                getUser().getFirstName());
    }
}
