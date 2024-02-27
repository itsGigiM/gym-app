package com.example.taskspring.service;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;

import javax.naming.AuthenticationException;

public interface ITrainerService {
    public void createTrainer(String firstName, String lastName, boolean isActive, Long trainerId,
                              TrainingType.TrainingTypeEnum specialization);

    public void updateTrainer(Long trainerId, Trainer trainer, String username, String password) throws AuthenticationException;

    public Trainer selectTrainer(Long trainerId, String username, String password) throws AuthenticationException;
}
