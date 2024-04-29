package com.example.taskspring.service;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.Set;

public interface TrainerService {
    public Trainer createTrainer(String firstName, String lastName, boolean isActive,
                              TrainingType specialization);
    public Trainer updateTrainer(Long trainerId, Trainer trainer);
    public Trainer selectTrainer(Long trainerId);
    public Trainer selectTrainer(String trainerUsername);
    public void changeTrainerPassword(Long trainerId, String newPassword);
    public void activateDeactivateTrainer(Long trainerId, boolean isActive);
    public Set<Training> getTrainerTrainingList(String trainerUsername, LocalDate fromDate,
                                                LocalDate toDate, String traineeName);
}
