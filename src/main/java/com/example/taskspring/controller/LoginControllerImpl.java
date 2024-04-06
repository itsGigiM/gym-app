package com.example.taskspring.controller;

import com.example.taskspring.actuator.metric.LoginMetrics;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.loginDTO.ChangePasswordDTO;
import com.example.taskspring.dto.loginDTO.TokenDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.User;
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
public class LoginControllerImpl implements LoginController {

    private TraineeService traineeService;
    private LoginMetrics loginMetrics;
    private TrainerService trainerService;
    private AuthenticationService authenticationService;

    @Autowired
    public LoginControllerImpl(TraineeService traineeService, LoginMetrics loginMetrics, TrainerService trainerService,
                               AuthenticationService authenticationService) {
        this.traineeService = traineeService;
        this.loginMetrics = loginMetrics;
        this.trainerService = trainerService;
        this.authenticationService = authenticationService;
    }
    @GetMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@ModelAttribute AuthenticationDTO request) throws AuthenticationException {
        log.info("Received GET request to login a user. Request details: {}", request);
        return ResponseEntity.ok(authenticationService.authenticate(request.getUsername(), request.getPassword()));
    }

    @PutMapping(value = "/login")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordDTO request) throws AuthenticationException {
        User user = findUser(request.getUsername());
        if(user == null){
            throw new AuthenticationException();
        }
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


    private User findUser(String username) {
        try {
            return traineeService.selectTrainee(username);
        } catch (EntityNotFoundException e) {
            try {
                return trainerService.selectTrainer(username);
            } catch (EntityNotFoundException en) {
                return null;
            }
        }
    }

    @GetMapping("/home")
    public ResponseEntity<HttpStatus> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
