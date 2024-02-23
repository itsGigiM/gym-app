package com.example.taskspring.service;

import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;

import java.time.Duration;
import java.time.LocalDate;

public interface ITrainingService {
    public String createTraining(String traineeId, String trainerId, String trainingName, TrainingType trainingType,
                                 LocalDate trainingDate, Duration trainingDuration);

    public Training selectTraining(String trainingId);
}
