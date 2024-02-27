package com.example.taskspring.service;


import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TrainingsRepository;
import com.example.taskspring.utils.Authenticator;
import lombok.AllArgsConstructor;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.taskspring.repository.TrainingsDAO;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

// Trainee Service class supports possibility to create/select Training profile.
@AllArgsConstructor
@Service
@Slf4j
public class TrainingService implements ITrainingService{


    private final TrainingsRepository repository;
    private final Authenticator authenticator;

    public void createTraining(Long trainingId, Trainee trainee, Trainer trainer, String trainingName, TrainingType.TrainingTypeEnum trainingType,
                               LocalDate trainingDate, Duration trainingDuration, String username, String password) throws AuthenticationException {
        Training training = new Training(trainingId, trainee, trainer, trainingName,
                trainingType, trainingDate, trainingDuration);
        authenticator.authenticate(username, password);
        repository.save(training);
        log.info("Created new trainer: " + training);
    }


    public Training selectTraining(Long trainingId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Training training = CheckTraining(trainingId);
        log.info("Selected trainer: " + training);
        return training;
    }

    public String toString(){
        return repository.toString();
    }

    private Training CheckTraining(Long trainingId) {
        Optional<Training> t =  repository.findById(trainingId);
        String errorMessage = "Training not found with ID: " + trainingId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            throw new NoSuchElementException(errorMessage);
        });
    }

}
