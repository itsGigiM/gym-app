package com.example.taskspring.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@NoArgsConstructor
public class LoginControllerImpl implements LoginController {

    private TraineeService traineeService;

    private TrainerService trainerService;
    @Autowired
    public LoginControllerImpl(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }
    @GetMapping(value = "/login")
    public ResponseEntity<HttpStatus> login(@RequestBody AuthenticationDTO request) {
        User user = findUser(request.getUsername());
        if(user == null){
            return failedLogin();
        }
        if(user.getPassword().equals(request.getPassword())){
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
            log.info("Welcome back {}!", user.getUsername());
            return responseEntity;
        }
        return failedLogin();
    }

    @PutMapping(value = "/login")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordDTO request) {
        User user = findUser(request.getUsername());
        if(user == null){
            return failedLogin();
        }
        if(user.getPassword().equals(request.getOldPassword())){
            if(user instanceof Trainee) {
                traineeService.changeTraineePassword(user.getUserId(), request.getNewPassword());
            }else trainerService.changeTrainerPassword(user.getUserId(), request.getNewPassword());
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
            log.info("Password changed successfully");
            return responseEntity;
        }
        return failedLogin();
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

    private static ResponseEntity<HttpStatus> failedLogin() {
        log.error("Authentication failed, wrong username or password.");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
