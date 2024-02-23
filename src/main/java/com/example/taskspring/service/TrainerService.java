package service;


import lombok.AllArgsConstructor;
import model.Trainer;
import org.springframework.stereotype.Service;
import repository.TrainersDAO;
import utils.PasswordGenerator;
import utils.UsernameGenerator;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Constants.PASSWORD_LENGTH;

// Trainee Service class supports possibility to create/update/select Trainer profile.
@AllArgsConstructor
@Service
public class TrainerService {

    private static final Logger logger = Logger.getLogger(TrainerService.class.getName());

    private TrainersDAO repository;
    public void createTrainer(String firstName, String lastName, boolean isActive, String userId,
                              String specialization){
        String trainerUsername = UsernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(PASSWORD_LENGTH);
        Trainer trainer = new Trainer(firstName, lastName, trainerUsername, password,
                isActive, specialization, userId);
        repository.add(trainer);
        logger.log(Level.INFO, "Created new trainer: " + trainer);
    }

    public void updateTrainer(String userId, Trainer trainer){
        if(trainer == null) throw new IllegalArgumentException("Trainer cannot be null");
        if(!repository.exists(userId)) throw new NoSuchElementException("User not found: " + userId);
        trainer.setUserId(userId);
        repository.set(trainer);
        logger.log(Level.INFO, "Updated trainer: " + trainer);
    }


    public Trainer selectTrainer(String userId){
        if(!repository.exists(userId)) throw new NoSuchElementException("User not found with ID: " + userId);
        Trainer trainer = repository.get(userId);
        logger.log(Level.INFO, "Selected trainer: " + trainer);
        return trainer;
    }

}
