package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

public interface ITraineeService {
    public void createTrainee(String firstName, String lastName, boolean isActive, Long traineeId,
                              String address, LocalDate dateOfBirth);

    public void updateTrainee(Long traineeId, Trainee trainee, String username, String password) throws AuthenticationException;

    public void deleteTrainee(Long traineeId, String username, String password) throws AuthenticationException;

    public Trainee selectTrainee(Long traineeId, String username, String password) throws AuthenticationException;
}
