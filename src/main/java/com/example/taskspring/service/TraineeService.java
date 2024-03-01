package com.example.taskspring.service;


import com.example.taskspring.repository.TraineesDAO;
import com.example.taskspring.utils.IDGenerator;
import com.example.taskspring.utils.IUsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import com.example.taskspring.model.Trainee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

// Trainee Service class supports possibility to create/update/delete/select Trainee profile.
@Service
@Slf4j
public class TraineeService implements ITraineeService{

    private final int passwordLength;
    private final TraineesDAO repository;
    private final IUsernameGenerator usernameGenerator;

    public TraineeService(TraineesDAO repository, IUsernameGenerator usernameGenerator,
                          @Value("${password.length}") int passwordLength){
        this.repository = repository;
        this.usernameGenerator = usernameGenerator;
        this.passwordLength = passwordLength;
    }

    public Long createTrainee(String firstName, String lastName, boolean isActive,
                               String address, LocalDate dateOfBirth){
        String username = usernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(passwordLength);
        Long traineeId = IDGenerator.generate();
        Trainee trainee = new Trainee(firstName, lastName, username, password,
                isActive, traineeId, address, dateOfBirth);
        repository.add(trainee);
        log.info("Created new trainee: " + trainee);
        return traineeId;
    }

    public void updateTrainee(Long traineeId, Trainee trainee){
        if(trainee == null){
            String errorMessage = "Trainee can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        CheckUser(traineeId);
        trainee.setTraineeId(traineeId);
        repository.set(trainee);
        log.info("Updated trainee: " + trainee);
    }

    public void deleteTrainee(Long traineeId){
        CheckUser(traineeId);
        repository.remove(traineeId);
        log.info("removed trainee #" + traineeId);
    }

    public Trainee selectTrainee(Long traineeId){
        CheckUser(traineeId);
        Trainee trainee = repository.get(traineeId);
        log.info("Selected trainee: " + trainee);
        return trainee;
    }

    private void CheckUser(Long traineeId) {
        if(repository.exists(traineeId)) return;
        String errorMessage = "User not found with ID: " + traineeId;
        log.error(errorMessage);
        throw new NoSuchElementException(errorMessage);
    }

}
