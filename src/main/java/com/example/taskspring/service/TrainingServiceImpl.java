package com.example.taskspring.service;


import com.example.taskspring.dto.trainingDTO.TrainerSessionWorkHoursUpdateDTO;
import com.example.taskspring.interfaces.DurationServiceInterface;
import com.example.taskspring.message.MessageProducer;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.TrainingsRepository;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    private TraineesRepository traineesRepository;

    private MessageProducer messageProducer;

    @Autowired
    public TrainingServiceImpl(TrainingsRepository repository, TrainersRepository trainersRepository,
                               TraineesRepository traineesRepository, MessageProducer messageProducer) {
        this.repository = repository;
        this.trainersRepository = trainersRepository;
        this.traineesRepository = traineesRepository;
        this.messageProducer = messageProducer;
    }

    @CircuitBreaker(name = "updateWorkHours")
    public Training createTraining(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType,
                                   LocalDate trainingDate, Duration trainingDuration, String token){
        if(invalidTraineeTrainer(trainee, trainer)){
            String errorMessage = "Trainer or Trainee not found";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        Training training = new Training(trainee, trainer, trainingName,
                trainingType, trainingDate, trainingDuration);
        Training savedTraining = repository.save(training);
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO(trainer.getUsername(),
                trainer.getFirstName(), trainer.getLastName(), trainer.isActive(), trainingDate,
                trainingDuration.toHours(), "ADD");

        messageProducer.sendWorkHoursUpdate(updateDTO, token);

        log.info("Created new training: " + training);
        return savedTraining;
    }

    private boolean invalidTraineeTrainer(Trainee trainee, Trainer trainer) {
        return trainee == null || trainer == null ||
                        traineesRepository.findByUsername(trainee.getUsername()).isEmpty() ||
                trainersRepository.findByUsername(trainer.getUsername()).isEmpty();
    }


    public Training selectTraining(Long trainingId){
        Training training = checkTraining(trainingId);
        log.info("Selected training: " + training);
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
