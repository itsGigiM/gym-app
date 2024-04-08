package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Token;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.User;
import com.example.taskspring.repository.repositories.TokenRepository;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.UserRepository;
import com.example.taskspring.service.AuthenticationServiceImpl;
import com.example.taskspring.service.JwtService;
import com.example.taskspring.service.LoginAttemptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticatorServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl sqlAuthenticator;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private LoginAttemptService loginAttemptService;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenRepository tokenRepository;

    @Test
    void authenticateWithValidTraineeCredentials() throws AuthenticationException {

        Trainee trainee = new Trainee("g", "m", "user", "pass",
                true, "t", LocalDate.of(2000,1,1));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(trainee));
        when(loginAttemptService.isBlocked()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(any())).thenReturn("token");
        when(tokenRepository.save(any())).thenReturn( new Token("token", trainee));


        sqlAuthenticator.authenticate("user", "pass");
    }

    @Test
    void authenticateWithInvalidCredentials() {

        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> sqlAuthenticator.authenticate("user", "pass"));
    }
}
