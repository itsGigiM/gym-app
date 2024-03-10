package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import org.springframework.data.repository.query.Param;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TraineeService {
    public Trainee createTrainee(String firstName, String lastName, boolean isActive,
                              String address, LocalDate dateOfBirth);

    public Trainee updateTrainee(Long traineeId, Trainee trainee);

    public void deleteTrainee(Long traineeId);
    public void deleteTrainee(String traineeUsername);
    public Trainee selectTrainee(Long traineeId);
    public Trainee selectTrainee(String traineeUsername);
    public void changeTraineePassword(Long traineeId, String newPassword);
    public void activateDeactivateTrainee(Long traineeId, boolean isActive);
    public void updateTrainersList(Long traineeId, Set<Trainer> trainers);
    public Set<Training> getTraineeTrainingList(String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerName,
                                                TrainingType trainingType);

    public Set<Trainer> getUnassignedTrainers(String traineeUsername);

}
