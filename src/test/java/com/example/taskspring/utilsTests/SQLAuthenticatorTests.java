package com.example.taskspring.utilsTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.utils.SQLAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SQLAuthenticatorTest {

    @Mock
    private TraineesRepository traineesRepository;

    @Mock
    private TrainersRepository trainersRepository;

    @InjectMocks
    private SQLAuthenticator sqlAuthenticator;

    @Test
    void authenticateWithValidTraineeCredentials() throws AuthenticationException {

        Trainee trainee = new Trainee("g", "m", "user", "pass",
                true, "t", LocalDate.of(2000,1,1));
        when(traineesRepository.findByUsername("user")).thenReturn(Optional.of(trainee));
        when(trainersRepository.findByUsername("user")).thenReturn(Optional.empty());

        sqlAuthenticator.authenticate("user", "pass");
    }

    @Test
    void authenticateWithInvalidCredentials() {

        when(traineesRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(trainersRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> sqlAuthenticator.authenticate("user", "pass"));
    }
}
