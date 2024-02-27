package com.example.taskspring.service;


import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.utils.IUsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import com.example.taskspring.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

// Trainee Service class supports possibility to create/update/delete/select Trainee profile.
@Service
@Slf4j
public class TraineeService implements ITraineeService{
    @Value("${password.length:10}")
    private int passwordLength;
    private final TraineesRepository repository;
    private final IUsernameGenerator usernameGenerator;
    private final Authenticator authenticator;

    public TraineeService(TraineesRepository repository, IUsernameGenerator usernameGenerator,
                            Authenticator authenticator){
        this.repository = repository;
        this.usernameGenerator = usernameGenerator;
        this.authenticator = authenticator;
    }

    public void createTrainee(String firstName, String lastName, boolean isActive, Long traineeId,
                               String address, LocalDate dateOfBirth){
        String username = usernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(passwordLength);
        Trainee trainee = new Trainee(firstName, lastName, username, password,
                isActive, traineeId, address, dateOfBirth);
        repository.save(trainee);
        log.info("Created new trainee: " + trainee);
    }

    public void updateTrainee(Long traineeId, Trainee trainee, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        if(trainee == null){
            String errorMessage = "Trainee can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        CheckUser(traineeId);
        trainee.setTraineeId(traineeId);
        repository.save(trainee);
        log.info("Updated trainee: " + trainee);
    }

    public void deleteTrainee(Long traineeId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = CheckUser(traineeId);
        repository.delete(t);
        log.info("removed trainee #" + traineeId);
    }

    public Trainee selectTrainee(Long traineeId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainee t = CheckUser(traineeId);
        log.info("Selected trainee: " + t);
        return t;
    }

    public String toString(){
        return repository.toString();
    }

    private Trainee CheckUser(Long traineeId) {
        Optional<Trainee> t =  repository.findById(traineeId);
        String errorMessage = "User not found with ID: " + traineeId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            throw new NoSuchElementException(errorMessage);
        });
    }

}
