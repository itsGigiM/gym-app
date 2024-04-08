package com.example.taskspring.service;


import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.utils.UsernameGenerator;
import com.example.taskspring.utils.PasswordGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

// Trainee Service class supports possibility to create/update/select Trainer profile.
@Service
@Slf4j
@NoArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private int passwordLength;
    private TrainersRepository repository;
    private UsernameGenerator usernameGenerator;

    private PasswordEncoder encoder;

    @Autowired
    public TrainerServiceImpl(UsernameGenerator usernameGenerator, TrainersRepository repository,
                              @Value("${password.length}") int passwordLength, PasswordEncoder encoder){
        this.repository = repository;
        this.usernameGenerator = usernameGenerator;
        this.passwordLength = passwordLength;
        this.encoder = encoder;
    }
    @Transactional
    public Trainer createTrainer(String firstName, String lastName, boolean isActive,
                              TrainingType specialization){
        String username = usernameGenerator.generateUsername(firstName, lastName);
        String generatedPass = PasswordGenerator.generatePassword(passwordLength);
        String password = encoder.encode(generatedPass);
        Trainer trainer = new Trainer(firstName, lastName, username,
                password, isActive, specialization);
        Trainer savedTrainer = repository.save(trainer);
        log.info("Created new trainer: " + savedTrainer);
        return new Trainer(firstName, lastName, username, generatedPass, isActive, specialization);
    }

    @Transactional
    public Trainer updateTrainer(Long trainerId, Trainer trainer){
        if(trainer == null){
            String errorMessage = "Trainer can not be null";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        checkUser(trainerId);
        trainer.setUserId(trainerId);
        Trainer savedTrainee = repository.save(trainer);
        log.info("Updated trainer: " + trainer);
        return savedTrainee;
    }


    public Trainer selectTrainer(Long trainerId){
        Trainer t = checkUser(trainerId);
        log.info("Selected trainer: " + t);
        return t;
    }

    public Trainer selectTrainer(String trainerUsername){
        Trainer t = checkUser(trainerUsername);
        log.info("Selected trainer: " + t);
        return t;
    }

    private Trainer checkUser(Long trainerId) {
        Optional<Trainer> t =  repository.findById(trainerId);
        String errorMessage = "User not found with ID: " + trainerId;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    private Trainer checkUser(String trainerUsername) {
        Optional<Trainer> t =  repository.findByUsername(trainerUsername);
        String errorMessage = "User not found with username: " + trainerUsername;
        return t.orElseThrow(() -> {
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    @Transactional
    public void changeTrainerPassword(Long trainerId, String newPassword){
        Trainer t = checkUser(trainerId);
        t.setPassword(newPassword);
        repository.save(t);
        log.info("Password changed for trainee #" + trainerId);
    }

    @Transactional
    public void activateDeactivateTrainer(Long trainerId, boolean isActive){
        Trainer t = checkUser(trainerId);
        t.setActive(isActive);
        repository.save(t);
        log.info("Active status changed for trainee #" + trainerId);
    }

    @Override
    public Set<Training> getTrainerTrainingList(String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeName) {
        repository.findByUsername(trainerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found with username: " + trainerUsername));
        return repository.findTrainerTrainings(trainerUsername, fromDate, toDate, traineeName);
    }

}
