package com.example.taskspring.controllerTests;

import com.example.taskspring.actuator.metric.LoginMetrics;
import com.example.taskspring.controller.LoginControllerImpl;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.loginDTO.ChangePasswordDTO;
import com.example.taskspring.dto.loginDTO.TokenDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.repositories.UserRepository;
import com.example.taskspring.service.AuthenticationService;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerImplTests {
    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private LoginMetrics loginMetrics;

    @InjectMocks
    private LoginControllerImpl loginController;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        loginController = new LoginControllerImpl(traineeService, loginMetrics, trainerService,
                authenticationService, userRepository);
    }

    @Test
    public void successfulLogin() throws AuthenticationException {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("username", "password");
        Trainee mockUser = new Trainee();
        mockUser.setUsername("username");
        mockUser.setPassword("password");

        when(authenticationService.authenticate(any(), any())).thenReturn(new TokenDTO("token"));

        ResponseEntity<TokenDTO> response = loginController.login(authenticationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void successfulChangePassword() throws AuthenticationException {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("u", "oldPassword", "newPassword");
        Trainee mockUser = new Trainee();
        mockUser.setUsername("u");
        mockUser.setPassword("oldPassword");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(mockUser));


        ResponseEntity<HttpStatus> response = loginController.changePassword(changePasswordDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void changePasswordWrongOldPassword_throwsException() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("username", "wrongPassword", "newPassword");
        Trainee mockUser = new Trainee();
        mockUser.setUsername("username");
        mockUser.setPassword("oldPassword");

        assertThrows(AuthenticationException.class, () -> {
            loginController.changePassword(changePasswordDTO);
        });
    }

    @Test
    public void changePasswordUserNotFound_throwsException(){
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("username", "oldPassword", "newPassword");

        assertThrows(AuthenticationException.class, () -> {
            loginController.changePassword(changePasswordDTO);
        });
    }


}
