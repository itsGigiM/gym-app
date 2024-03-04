package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.Set;

public interface TraineeService {
    public Trainee createTrainee(String firstName, String lastName, boolean isActive,
                              String address, LocalDate dateOfBirth);

    public void updateTrainee(Long traineeId, Trainee trainee, String username, String password) throws AuthenticationException;

    public void deleteTrainee(Long traineeId, String username, String password) throws AuthenticationException;
    public void deleteTrainee(String traineeUsername, String username, String password) throws AuthenticationException;
    public Trainee selectTrainee(Long traineeId, String username, String password) throws AuthenticationException;
    public void changeTraineePassword(Long traineeId, String newPassword, String username, String password) throws AuthenticationException;
    public void activateDeactivateTrainee(Long traineeId, boolean isActive, String username, String password) throws AuthenticationException;
    public void updateTrainersList(Long traineeId, Set<Trainer> trainers);
}
