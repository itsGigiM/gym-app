package service;


import lombok.AllArgsConstructor;
import model.Training;
import model.TrainingType;
import org.springframework.stereotype.Service;
import repository.TrainingsDAO;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Constants.PASSWORD_LENGTH;

// Trainee Service class supports possibility to create/select Training profile.
@AllArgsConstructor
@Service
public class TrainingService {

    private static final Logger logger = Logger.getLogger(TrainerService.class.getName());

    private TrainingsDAO repository;
    public String createTraining(String traineeId, String trainerId, String trainingName, TrainingType trainingType,
                               Date trainingDate, Duration trainingDuration){
        String trainingId = "123";
        Training training = new Training(trainingId, traineeId, trainerId, trainingName,
                trainingType, trainingDate, trainingDuration);
        repository.add(training);
        logger.log(Level.INFO, "Created new trainer: " + training);
        return trainingId;
    }


    public Training selectTraining(String trainingId){
        if(!repository.exists(trainingId)) throw new NoSuchElementException("Training not found with ID: " + trainingId);
        Training training = repository.get(trainingId);
        logger.log(Level.INFO, "Selected trainer: " + training);
        return training;
    }

}
