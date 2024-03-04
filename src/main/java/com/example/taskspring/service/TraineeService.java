package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.UsersRepository;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.utils.IUsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import com.example.taskspring.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
public class TraineeService implements ITraineeService{
    private final int passwordLength;
    private final TraineesRepository repository;
    private final UsersRepository usersRepository;
    private final IUsernameGenerator usernameGenerator;
    private final Authenticator authenticator;

    public TraineeService(TraineesRepository repository, IUsernameGenerator usernameGenerator,
                          Authenticator authenticator, @Value("${password.length}") int passwordLength, UsersRepository usersRepository){
        this.usernameGenerator = usernameGenerator;
        this.authenticator = authenticator;
        this.passwordLength = passwordLength;
        this.repository = repository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Trainee createTrainee(String firstName, String lastName, boolean isActive,
                               String address, LocalDate dateOfBirth){
        String password = PasswordGenerator.generatePassword(passwordLength);
        String username = usernameGenerator.generateUsername(firstName, lastName);
        User user = new User(firstName, lastName, username,
                password, isActive);
        usersRepository.save(user);
        log.info("Created new user: " + user);
        Trainee trainee = new Trainee(user, address, dateOfBirth);
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
        trainee.setTraineeId(traineeId);
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

    @Transactional
    public void deleteTrainee(String traineeUsername, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = repository.findByUserUsername(traineeUsername)
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
        t.getUser().setPassword(newPassword);
        repository.save(t);
        log.info("Password changed for trainee #" + traineeId);
    }

    @Transactional
    public void activateDeactivateTrainee(Long traineeId, boolean isActive, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = checkUser(traineeId);
        t.getUser().setActive(isActive);
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

    public String toString(){
        return repository.toString();
    }

    private Trainee checkUser(Long traineeId) {
        Optional<Trainee> t =  repository.findById(traineeId);
        String errorMessage = "User not found with ID: " + traineeId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        });
    }

}
