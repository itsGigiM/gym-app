package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.utils.UsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import com.example.taskspring.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

// Trainee Service class supports possibility to create/update/delete/select Trainee profile.
@Service
@Slf4j
@NoArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private int passwordLength;
    private TraineesRepository repository;
    private UsernameGenerator usernameGenerator;
    private Authenticator authenticator;

    @Autowired
    public TraineeServiceImpl(TraineesRepository repository, UsernameGenerator usernameGenerator,
                              Authenticator authenticator, @Value("${password.length}") int passwordLength){
        this.usernameGenerator = usernameGenerator;
        this.authenticator = authenticator;
        this.passwordLength = passwordLength;
        this.repository = repository;
    }

    @Transactional
    public Trainee createTrainee(String firstName, String lastName, boolean isActive,
                               String address, LocalDate dateOfBirth){
        String password = PasswordGenerator.generatePassword(passwordLength);
        String username = usernameGenerator.generateUsername(firstName, lastName);
        Trainee trainee = new Trainee(firstName, lastName, username, password, isActive, address, dateOfBirth);
        Trainee savedTrainee = repository.save(trainee);
        log.info("Created new trainee: " + trainee);
        return savedTrainee;
    }

    @Transactional
    public void updateTrainee(Long traineeId, Trainee trainee, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        if(trainee == null){
            String errorMessage = "Trainee can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        checkUser(traineeId);
        trainee.setUserId(traineeId);
        Trainee savedTrainee = repository.save(trainee);
        log.info("Updated trainee: " + savedTrainee);
    }

    @Transactional
    public void deleteTrainee(Long traineeId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = checkUser(traineeId);
        repository.delete(t);
        log.info("removed trainee #" + traineeId);
    }

    public void deleteTrainee(String traineeUsername, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = repository.findByUsername(traineeUsername)
                .orElseThrow(() -> new NoSuchElementException("Trainee not found with username: " + traineeUsername));
        repository.delete(t);
        log.info("removed trainee " + traineeUsername);
    }

    public Trainee selectTrainee(Long traineeId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = checkUser(traineeId);
        log.info("Selected trainee: " + t);
        return t;
    }

    @Transactional
    public void changeTraineePassword(Long traineeId, String newPassword, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = checkUser(traineeId);
        t.setPassword(newPassword);
        repository.save(t);
        log.info("Password changed for trainee #" + traineeId);
    }

    @Transactional
    public void activateDeactivateTrainee(Long traineeId, boolean isActive, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = checkUser(traineeId);
        t.setActive(isActive);
        repository.save(t);
        log.info("Active status changed for trainee #" + traineeId);
    }

    @Transactional
    public void updateTrainersList(Long traineeId, Set<Trainer> trainers, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = repository.findById(traineeId)
                .orElseThrow(() -> new NoSuchElementException("Trainee not found with ID: " + traineeId));
        t.setTrainers(trainers);
        repository.save(t);
        log.info("trainee #" + traineeId + "'s trainers list updated");
    }

    private Trainee checkUser(Long traineeId) {
        Optional<Trainee> t =  repository.findById(traineeId);
        String errorMessage = "User not found with ID: " + traineeId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

}
