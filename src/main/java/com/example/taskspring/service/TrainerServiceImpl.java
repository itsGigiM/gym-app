package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.model.User;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.repository.UsersRepository;
import com.example.taskspring.utils.Authenticator;
import com.example.taskspring.utils.UsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

// Trainee Service class supports possibility to create/update/select Trainer profile.
@Service
@Slf4j
@NoArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private int passwordLength;
    private TrainersRepository repository;
    private UsersRepository usersRepository;
    private UsernameGenerator usernameGenerator;

    private Authenticator authenticator;


    @Autowired
    public TrainerServiceImpl(UsernameGenerator usernameGenerator, TrainersRepository repository,
                              Authenticator authenticator, @Value("${password.length}") int passwordLength, UsersRepository usersRepository){
        this.repository = repository;
        this.usernameGenerator = usernameGenerator;
        this.authenticator = authenticator;
        this.passwordLength = passwordLength;
        this.usersRepository = usersRepository;
    }
    @Transactional
    public Trainer createTrainer(String firstName, String lastName, boolean isActive,
                              TrainingType specialization){
        String username = usernameGenerator.generateUsername(firstName, lastName);
        String password = PasswordGenerator.generatePassword(passwordLength);
        User user = new User(firstName, lastName, username,
                password, isActive);
        usersRepository.save(user);
        log.info("Created new user: " + user);
        Trainer trainer = new Trainer(user, specialization);
        Trainer savedTrainer = repository.save(trainer);
        log.info("Created new trainer: " + trainer);
        return savedTrainer;
    }

    @Transactional
    public void updateTrainer(Long trainerId, Trainer trainer, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        if(trainer == null){
            String errorMessage = "Trainer can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        checkUser(trainerId);
        trainer.setTrainerId(trainerId);
        repository.save(trainer);
        log.info("Updated trainer: " + trainer);
    }


    public Trainer selectTrainer(Long trainerId, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainer t = checkUser(trainerId);
        log.info("Selected trainer: " + t);
        return t;
    }

    private Trainer checkUser(Long trainerId) {
        Optional<Trainer> t =  repository.findById(trainerId);
        String errorMessage = "User not found with ID: " + trainerId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        });
    }

    @Transactional
    public void changeTrainerPassword(Long trainerId, String newPassword, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainer t = checkUser(trainerId);
        t.getUser().setPassword(newPassword);
        repository.save(t);
        log.info("Password changed for trainee #" + trainerId);
    }

    @Transactional
    public void activateDeactivateTrainer(Long trainerId, boolean isActive, String username, String password) throws AuthenticationException {
        authenticator.authenticate(username, password);
        Trainer t = checkUser(trainerId);
        t.getUser().setActive(isActive);
        repository.save(t);
        log.info("Active status changed for trainee #" + trainerId);
    }

}
