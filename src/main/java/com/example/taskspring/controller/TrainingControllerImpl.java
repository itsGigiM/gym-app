package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import com.example.taskspring.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
@Slf4j
@NoArgsConstructor
public class TrainingControllerImpl implements TrainingController {

    private TrainingService trainingService;
    private TrainerService trainerService;
    private TraineeService traineeService;

    @Autowired
    public TrainingControllerImpl(TrainingService trainingService, TrainerService trainerService,
                                  TraineeService traineeService) {
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody PostTrainingRequest request) {
        log.info("Received POST request to create a training. Request details: {}", request);
        try {
            Trainer trainer = trainerService.selectTrainer(request.getTrainerUsername());
            Trainee trainee = traineeService.selectTrainee(request.getTraineeUsername());
            trainingService.createTraining(trainee, trainer,
                    request.getTrainingName(), trainer.getSpecialization(), request.getTrainingDate(),
                    request.getTrainingDuration());
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
            log.info("Trainer created successfully. Response details: {}", responseEntity);
            return responseEntity;
        } catch (EntityNotFoundException e) {
            log.error("Trainee's or Trainer's username is not valid. Check or register a user first");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
