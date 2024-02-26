package com.example.taskspring.service;

import com.example.taskspring.model.Trainer;

public interface ITrainerService {
    public void createTrainer(String firstName, String lastName, boolean isActive, Long trainerId,
                              String specialization);

    public void updateTrainer(Long trainerId, Trainer trainer);

    public Trainer selectTrainer(Long trainerId);
}
