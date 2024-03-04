package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;

import java.time.LocalDate;

public interface TraineeService {
    public Long createTrainee(String firstName, String lastName, boolean isActive,
                              String address, LocalDate dateOfBirth);

    public void updateTrainee(Long traineeId, Trainee trainee);

    public void deleteTrainee(Long traineeId);

    public Trainee selectTrainee(Long traineeId);
}
