package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.utils.IDGenerator;
import com.example.taskspring.utils.UsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.taskspring.repository.TrainersDAO;
import java.util.NoSuchElementException;

// Trainee Service class supports possibility to create/update/select Trainer profile.
@Service
@Slf4j
@NoArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private int passwordLength;
    private TrainersDAO repository;
    private UsernameGenerator usernameGenerator;

    public TrainerServiceImpl(TrainersDAO repository, UsernameGenerator usernameGenerator,
                              @Value("${password.length}") int passwordLength){
        this.repository = repository;
        this.usernameGenerator = usernameGenerator;
        this.passwordLength = passwordLength;
    }
    public Long createTrainer(String firstName, String lastName, boolean isActive,
                              TrainingType specialization){
        String username = usernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(passwordLength);
        Long trainerId = IDGenerator.generate();
        Trainer trainer = new Trainer(firstName, lastName, username, password,
                isActive, specialization, trainerId);
        repository.add(trainer);
        log.info("Created new trainer: " + trainer);
        return trainerId;
    }

    public void updateTrainer(Long trainerId, Trainer trainer){
        if(trainer == null){
            String errorMessage = "Trainer can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        checkUser(trainerId);
        trainer.setTrainerId(trainerId);
        repository.set(trainer);
        log.info("Updated trainer: " + trainer);
    }


    public Trainer selectTrainer(Long trainerId){
        checkUser(trainerId);
        Trainer trainer = repository.get(trainerId);
        log.info("Selected trainer: " + trainer);
        return trainer;
    }

    private void checkUser(Long trainerId) {
        if(repository.exists(trainerId)) return;
        String errorMessage = "User not found with ID: " + trainerId;
        log.error(errorMessage);
        throw new NoSuchElementException(errorMessage);
    }

}
