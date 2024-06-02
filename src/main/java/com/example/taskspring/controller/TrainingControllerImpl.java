package com.example.taskspring.controller;

import com.example.taskspring.actuator.metric.TrainingMetrics;
import com.example.taskspring.config.FeignClientInterceptor;
import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import com.example.taskspring.service.TrainingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
@Slf4j
@NoArgsConstructor
@CrossOrigin(origins = "http://epam.com", maxAge = 3600)
public class TrainingControllerImpl implements TrainingController {

    private TrainingService trainingService;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private TrainingMetrics trainingMetrics;

    private FeignClientInterceptor interceptor;
    @Autowired
    public TrainingControllerImpl(TrainingService trainingService, TrainerService trainerService,
                                  TraineeService traineeService, TrainingMetrics trainingMetrics,
                                  FeignClientInterceptor interceptor) {
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingMetrics = trainingMetrics;
        this.interceptor = interceptor;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody PostTrainingRequest request, HttpServletRequest httpServletRequest){
        log.info("Received POST request to create a training. Request details: {}", request);
        Trainer trainer = trainerService.selectTrainer(request.getTrainerUsername());
        Trainee trainee = traineeService.selectTrainee(request.getTraineeUsername());
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        interceptor.setToken(token);
        trainingService.createTraining(trainee, trainer,
                request.getTrainingName(), trainer.getSpecialization(), request.getTrainingDate(),
                request.getTrainingDuration(), token);
        ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        trainingMetrics.incrementTrainingsCreatedCounter();
        log.info("Trainer created successfully. Response details: {}", responseEntity);

        return responseEntity;
    }

}
