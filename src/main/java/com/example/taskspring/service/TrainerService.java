package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.utils.IUsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.taskspring.repository.TrainersDAO;
import java.util.NoSuchElementException;

// Trainee Service class supports possibility to create/update/select Trainer profile.
@Service
@Slf4j
public class TrainerService implements ITrainerService{

    @Value("${password.length}")
    private int passwordLength = 10;
    private final TrainersDAO repository;
    private final IUsernameGenerator usernameGenerator;

    public TrainerService(TrainersDAO repository, IUsernameGenerator usernameGenerator){
        this.repository = repository;
        this.usernameGenerator = usernameGenerator;
    }
    public void createTrainer(String firstName, String lastName, boolean isActive, Long trainerId,
                              String specialization){
        String username = usernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(passwordLength);
        Trainer trainer = new Trainer(firstName, lastName, username, password,
                isActive, specialization, trainerId);
        repository.add(trainer);
        log.info("Created new trainer: " + trainer);
    }

    public void updateTrainer(Long trainerId, Trainer trainer){
        if(trainer == null){
            String errorMessage = "Trainer can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        CheckUser(trainerId);
        trainer.setTrainerId(trainerId);
        repository.set(trainer);
        log.info("Updated trainer: " + trainer);
    }


    public Trainer selectTrainer(Long trainerId){
        CheckUser(trainerId);
        Trainer trainer = repository.get(trainerId);
        log.info("Selected trainer: " + trainer);
        return trainer;
    }

    private void CheckUser(Long trainerId) {
        if(repository.exists(trainerId)) return;
        String errorMessage = "User not found with ID: " + trainerId;
        log.error(errorMessage);
        throw new NoSuchElementException(errorMessage);
    }

}