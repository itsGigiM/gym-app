package com.example.taskspring.service;


import lombok.AllArgsConstructor;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.taskspring.repository.TrainingsDAO;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;

// Trainee Service class supports possibility to create/select Training profile.
@AllArgsConstructor
@Service
@Slf4j
public class TrainingService implements ITrainingService{


    private TrainingsDAO repository;
    public String createTraining(String traineeId, String trainerId, String trainingName, TrainingType trainingType,
                                 LocalDate trainingDate, Duration trainingDuration){
        String trainingId = "123";
        Training training = new Training(trainingId, traineeId, trainerId, trainingName,
                trainingType, trainingDate, trainingDuration);
        repository.add(training);
        log.info("Created new trainer: " + training);
        return trainingId;
    }


    public Training selectTraining(String trainingId){
        CheckTraining(trainingId);
        Training training = repository.get(trainingId);
        log.info("Selected trainer: " + training);
        return training;
    }

    public String toString(){
        return repository.toString();
    }

    private void CheckTraining(String trainingId) {
        if(repository.exists(trainingId)) return;
        String errorMessage = "Training not found with ID: " + trainingId;
        log.error(errorMessage);
        throw new NoSuchElementException(errorMessage);
    }

}
