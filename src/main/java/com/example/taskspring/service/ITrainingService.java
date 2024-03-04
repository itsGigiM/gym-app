package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;

public interface ITrainingService {
    public Training createTraining(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType,
                               LocalDate trainingDate, Duration trainingDuration, String username, String password) throws AuthenticationException;

    public Training selectTraining(Long trainingId, String username, String password) throws AuthenticationException;
}
