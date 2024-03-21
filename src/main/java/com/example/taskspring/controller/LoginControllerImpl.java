package com.example.taskspring.controller;

import com.example.taskspring.actuator.metric.LoginMetrics;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.loginDTO.ChangePasswordDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.User;
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
    @Autowired
    public LoginControllerImpl(TraineeService traineeService, TrainerService trainerService, LoginMetrics loginMetrics) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.loginMetrics = loginMetrics;
    }

    @GetMapping(value = "/login")
    public ResponseEntity<HttpStatus> login(@ModelAttribute AuthenticationDTO request) throws AuthenticationException {
        User user = findUser(request.getUsername());
        if(user == null){
            throw new AuthenticationException();
        }
        if(user.getPassword().equals(request.getPassword())){
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
            log.info("Welcome back {}!", user.getUsername());
            loginMetrics.incrementSuccessfulCounter();
            return responseEntity;
        }
        throw new AuthenticationException();
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

}
