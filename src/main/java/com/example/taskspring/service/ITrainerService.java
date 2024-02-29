package com.example.taskspring.service;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;

public interface ITrainerService {
    public void createTrainer(String firstName, String lastName, boolean isActive, Long trainerId,
                              TrainingType specialization);

    public void updateTrainer(Long trainerId, Trainer trainer);

    public Trainer selectTrainer(Long trainerId);
}
