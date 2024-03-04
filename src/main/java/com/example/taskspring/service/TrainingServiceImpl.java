package com.example.taskspring.service;


import lombok.AllArgsConstructor;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class TrainingServiceImpl implements TrainingService {


    private TrainingsDAO repository;
    public void createTraining(Long trainingId, Long traineeId, Long trainerId, String trainingName, TrainingType trainingType,
                                 LocalDate trainingDate, Duration trainingDuration){
        Training training = new Training(trainingId, traineeId, trainerId, trainingName,
                trainingType, trainingDate, trainingDuration);
        repository.add(training);
        log.info("Created new trainer: " + training);
    }


    public Training selectTraining(Long trainingId){
        checkTraining(trainingId);
        Training training = repository.get(trainingId);
        log.info("Selected trainer: " + training);
        return training;
    }

    private void checkTraining(Long trainingId) {
        if(repository.exists(trainingId)) return;
        String errorMessage = "Training not found with ID: " + trainingId;
        log.error(errorMessage);
        throw new NoSuchElementException(errorMessage);
    }

}
