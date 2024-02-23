package service;


import lombok.AllArgsConstructor;
import model.Trainee;
import org.springframework.stereotype.Service;
import repository.TraineesDAO;
import utils.PasswordGenerator;
import utils.UsernameGenerator;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Constants.PASSWORD_LENGTH;

// Trainee Service class supports possibility to create/update/delete/select Trainee profile.
@AllArgsConstructor
@Service
public class TraineeService {

    private static final Logger logger = Logger.getLogger(TraineeService.class.getName());

    private TraineesDAO repository;
    public void createTrainee(String firstName, String lastName, boolean isActive, String userId,
                               String address, Date dateOfBirth){
        String username = UsernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(PASSWORD_LENGTH);
        Trainee trainee = new Trainee(firstName, lastName, username, password,
                isActive, userId, address, dateOfBirth);
        repository.add(trainee);
        logger.log(Level.INFO, "Created new trainee: " + trainee);
    }

    public void updateTrainee(String userId, Trainee trainee){
        if(trainee == null) throw new IllegalArgumentException("Trainee cannot be null");
        if(!repository.exists(userId)) throw new NoSuchElementException("User not found: " + userId);
        trainee.setUserId(userId);
        repository.set(trainee);
        logger.log(Level.INFO, "Updated trainee: " + trainee);
    }

    public void deleteTrainee(String userId){
        if(!repository.exists(userId)) throw new NoSuchElementException("User not found with ID: " + userId);
        repository.remove(userId);
        logger.log(Level.INFO, "removed trainee #" + userId);
    }

    public Trainee selectTrainee(String userId){
        if(!repository.exists(userId)) throw new NoSuchElementException("User not found with ID: " + userId);
        Trainee trainee = repository.get(userId);
        logger.log(Level.INFO, "Selected trainee: " + trainee);
        return trainee;
    }

}
