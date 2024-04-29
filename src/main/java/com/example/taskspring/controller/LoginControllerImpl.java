package com.example.taskspring.controller;

import com.example.taskspring.actuator.metric.LoginMetrics;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.loginDTO.ChangePasswordDTO;
import com.example.taskspring.dto.loginDTO.TokenDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.User;
import com.example.taskspring.repository.repositories.UserRepository;
import com.example.taskspring.service.AuthenticationService;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@Slf4j
@NoArgsConstructor
@CrossOrigin(origins = "http://epam.com", maxAge = 3600)
public class LoginControllerImpl implements LoginController {

    private TraineeService traineeService;
    private LoginMetrics loginMetrics;
    private TrainerService trainerService;
    private AuthenticationService authenticationService;
    private UserRepository userRepository;

    @Autowired
    public LoginControllerImpl(TraineeService traineeService, LoginMetrics loginMetrics, TrainerService trainerService,
                               AuthenticationService authenticationService, UserRepository userRepository) {
        this.traineeService = traineeService;
        this.loginMetrics = loginMetrics;
        this.trainerService = trainerService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }
    @GetMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@ModelAttribute AuthenticationDTO request) throws AuthenticationException {
        log.info("Received GET request to login a user. Request details: {}", request);
        return ResponseEntity.ok(authenticationService.authenticate(request.getUsername(), request.getPassword()));
    }

    @PutMapping(value = "/login")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordDTO request) throws AuthenticationException {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                AuthenticationException::new
        );
        if(user.getPassword().equals(request.getOldPassword())){
            if(user instanceof Trainee) {
                traineeService.changeTraineePassword(user.getUserId(), request.getNewPassword());
            }else trainerService.changeTrainerPassword(user.getUserId(), request.getNewPassword());
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            log.info("Password changed successfully");
            loginMetrics.incrementPasswordChangeCounter();
            return responseEntity;
        }
        throw new AuthenticationException();
    }

}
