package com.example.taskspring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@AllArgsConstructor
@Service
public class GymAppFacade {
    private TraineeService traineeService;
    private TrainingService trainingService;
    private TrainerService trainerService;

}
