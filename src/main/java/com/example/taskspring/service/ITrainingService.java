package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;

public interface ITrainingService {
    public void createTraining(Long trainingId, Trainee trainee, Trainer trainer, String trainingName, TrainingType.TrainingTypeEnum trainingType,
                               LocalDate trainingDate, Duration trainingDuration, String username, String password) throws AuthenticationException;

    public Training selectTraining(Long trainingId, String username, String password) throws AuthenticationException;
}
