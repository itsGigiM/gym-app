package com.example.taskspring.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Service
public class GymAppFacade {
    private ITraineeService traineeService;
    private ITrainingService trainingService;
    private ITrainerService trainerService;

}
