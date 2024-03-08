package com.example.taskspring.service;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;

import javax.naming.AuthenticationException;

public interface TrainerService {
    public Trainer createTrainer(String firstName, String lastName, boolean isActive,
                              TrainingType specialization);
    public void updateTrainer(Long trainerId, Trainer trainer, String username, String password) throws AuthenticationException;
    public Trainer selectTrainer(Long trainerId, String username, String password) throws AuthenticationException;
    public Trainer selectTrainer(String trainerUsername, String username, String password) throws AuthenticationException;
    public void changeTrainerPassword(Long trainerId, String newPassword, String username, String password) throws AuthenticationException;
    public void activateDeactivateTrainer(Long trainerId, boolean isActive, String username, String password) throws AuthenticationException;
}
