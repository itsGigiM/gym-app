package com.example.taskspring.service;


import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.repository.TrainingsRepository;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

// Trainee Service class supports possibility to create/select Training profile.
@Service
@Slf4j
@NoArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private TrainingsRepository repository;

    private TrainersRepository trainersRepository;
    private Authenticator authenticator;

    @Autowired
    public TrainingServiceImpl(TrainingsRepository repository, TrainersRepository trainersRepository,
                               Authenticator authenticator) {
        this.repository = repository;
        this.trainersRepository = trainersRepository;
        this.authenticator = authenticator;
    }

    public Training createTraining(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType,
                                   LocalDate trainingDate, Duration trainingDuration, String username, String password) throws AuthenticationException {
        if(invalidTraineeTrainer(trainee, trainer)){
            String errorMessage = "Trainer or Trainee not found";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        Training training = new Training(trainee, trainer, trainingName,
                trainingType, trainingDate, trainingDuration);
        authenticator.authenticate(username, password);
        Training savedTraining = repository.save(training);
        log.info("Created new trainer: " + training);
        return savedTraining;
    }

    private boolean invalidTraineeTrainer(Trainee trainee, Trainer trainer) {
        return trainee == null || trainer == null ||
                        trainersRepository.findByUsername(trainee.getUsername()).isEmpty() ||
                trainersRepository.findByUsername(trainer.getUsername()).isEmpty();
    }


    public Training selectTraining(Long trainingId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Training training = checkTraining(trainingId);
        log.info("Selected trainer: " + training);
        return training;
    }

    private Training checkTraining(Long trainingId) {
        Optional<Training> t =  repository.findById(trainingId);
        String errorMessage = "Training not found with ID: " + trainingId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            return new NoSuchElementException(errorMessage);
        });
    }

}
