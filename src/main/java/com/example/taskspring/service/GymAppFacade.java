package com.example.taskspring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@AllArgsConstructor
@Service
public class GymAppFacade {
    private ITraineeService traineeService;
    private ITrainingService trainingService;
    private ITrainerService trainerService;

}
