package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;

import java.time.LocalDate;

public interface ITraineeService {
    public void createTrainee(String firstName, String lastName, boolean isActive, String traineeId,
                              String address, LocalDate dateOfBirth);

    public void updateTrainee(String traineeId, Trainee trainee);

    public void deleteTrainee(String traineeId);

    public Trainee selectTrainee(String traineeId);
}
