package com.example.taskspring.service;

import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;

import java.time.Duration;
import java.time.LocalDate;

public interface TrainingService {
    public void createTraining(Long trainingId, Long traineeId, Long trainerId, String trainingName, TrainingType trainingType,
                                 LocalDate trainingDate, Duration trainingDuration);

    public Training selectTraining(Long trainingId);
}
