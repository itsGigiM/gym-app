package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TokenRepository;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.utils.UsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import com.example.taskspring.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private PasswordEncoder encoder;

    @Autowired
    public TraineeServiceImpl(TraineesRepository repository, UsernameGenerator usernameGenerator,
                              @Value("${password.length}") int passwordLength, PasswordEncoder encoder){
        this.usernameGenerator = usernameGenerator;
        this.passwordLength = passwordLength;
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public Trainee createTrainee(String firstName, String lastName, boolean isActive,
                               String address, LocalDate dateOfBirth){
        String generatedPass = PasswordGenerator.generatePassword(passwordLength);
        String password = encoder.encode(generatedPass);
        String username = usernameGenerator.generateUsername(firstName, lastName);
        Trainee trainee = new Trainee(firstName, lastName, username, password, isActive, address, dateOfBirth);
        Trainee savedTrainee = repository.save(trainee);
        log.info("Created new trainee: " + savedTrainee);
        return new Trainee(firstName, lastName, username, generatedPass, isActive, address, dateOfBirth);
    }

    @Transactional
    public Trainee updateTrainee(Long traineeId, Trainee trainee) {
        if(trainee == null){
            String errorMessage = "Trainee can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        checkUser(traineeId);
        trainee.setUserId(traineeId);
        Trainee savedTrainee = repository.save(trainee);
        log.info("Updated trainee: " + savedTrainee);
        return savedTrainee;
    }

    @Transactional
    public void deleteTrainee(Long traineeId) {
        Trainee t = checkUser(traineeId);
        repository.delete(t);
        log.info("removed trainee #" + traineeId);
    }

    public void deleteTrainee(String traineeUsername) {
        Trainee t = repository.findByUsername(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with username: " + traineeUsername));
        repository.delete(t);
        log.info("removed trainee " + traineeUsername);
    }

    public Trainee selectTrainee(Long traineeId) {
        Trainee t = checkUser(traineeId);
        log.info("Selected trainee: " + t);
        return t;
    }

    public Trainee selectTrainee(String traineeUsername) {
        Trainee t = checkUser(traineeUsername);
        log.info("Selected trainee: " + t);
        return t;
    }

    @Transactional
    public void changeTraineePassword(Long traineeId, String newPassword) {
        Trainee t = checkUser(traineeId);
        t.setPassword(newPassword);
        repository.save(t);
        log.info("Password changed for trainee #" + traineeId);
    }

    @Transactional
    public void activateDeactivateTrainee(Long traineeId, boolean isActive) {
        Trainee t = checkUser(traineeId);
        t.setActive(isActive);
        repository.save(t);
        log.info("Active status changed for trainee #" + traineeId);
    }

    @Transactional
    public void updateTrainersList(Long traineeId, Set<Trainer> trainers) {
        Trainee t = repository.findById(traineeId)
                .orElseThrow(() -> new NoSuchElementException("Trainee not found with ID: " + traineeId));
        t.setTrainers(trainers);
        repository.save(t);
        log.info("trainee #" + traineeId + "'s trainers list updated");
    }

    @Override
    public Set<Training> getTraineeTrainingList(String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerName,
                                       TrainingType trainingType) {
        repository.findByUsername(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with username: " + traineeUsername));
        return repository.findTraineeTrainings(traineeUsername, fromDate, toDate, trainerName, trainingType);
    }

    @Override
    public Set<Trainer> getUnassignedTrainers(String traineeUsername) {
        repository.findByUsername(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with username: " + traineeUsername));
        return repository.findUnassignedTrainers(traineeUsername);
    }

    private Trainee checkUser(Long traineeId) {
        Optional<Trainee> t =  repository.findById(traineeId);
        String errorMessage = "User not found with ID: " + traineeId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    private Trainee checkUser(String traineeUsername) {
        Optional<Trainee> t =  repository.findByUsername(traineeUsername);
        String errorMessage = "User not found with username: " + traineeUsername;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

}
