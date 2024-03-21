package com.example.taskspring.controller;

import com.example.taskspring.actuator.metric.TrainingMetrics;
import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.service.AuthenticationService;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import com.example.taskspring.service.TrainingService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/trainings")
@Slf4j
@NoArgsConstructor
public class TrainingControllerImpl implements TrainingController {

    private TrainingService trainingService;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private TrainingMetrics trainingMetrics;
    private AuthenticationService authenticationService;

    @Autowired
    public TrainingControllerImpl(TrainingService trainingService, TrainerService trainerService,
                                  TraineeService traineeService, AuthenticationService authenticationService,
                                  TrainingMetrics trainingMetrics) {
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.authenticationService = authenticationService;
        this.trainingMetrics = trainingMetrics;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody PostTrainingRequest request, @RequestParam String username,
                                             @RequestParam String password) throws AuthenticationException {
        authenticationService.authenticate(username, password);
        log.info("Received POST request to create a training. Request details: {}", request);
        Trainer trainer = trainerService.selectTrainer(request.getTrainerUsername());
        Trainee trainee = traineeService.selectTrainee(request.getTraineeUsername());
        trainingService.createTraining(trainee, trainer,
                request.getTrainingName(), trainer.getSpecialization(), request.getTrainingDate(),
                request.getTrainingDuration());
        ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        trainingMetrics.incrementTrainingsCreatedCounter();
        log.info("Trainer created successfully. Response details: {}", responseEntity);

        return responseEntity;
    }

}
