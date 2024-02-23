package com.example.taskspring.service;

import com.example.taskspring.model.Trainer;

public interface ITrainerService {
    public void createTrainer(String firstName, String lastName, boolean isActive, String trainerId,
                              String specialization);

    public void updateTrainer(String trainerId, Trainer trainer);

    public Trainer selectTrainer(String trainerId);
}
