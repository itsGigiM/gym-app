package com.example.taskspring.repositoryTests;

import com.example.taskspring.model.*;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.TrainingTypeRepository;
import com.example.taskspring.repository.repositories.TrainingsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TraineesRepositoryTests {
    @Autowired
    private TraineesRepository traineesRepository;

    @Autowired
    private TrainersRepository trainersRepository;

    @Autowired
    private TrainingsRepository trainingsRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;


    @Test
    public void addTraineeAndRetrieveItFromRepository() {

        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Tbilisi", LocalDate.of(2022, 2, 2));

        Trainee savedTrainee = traineesRepository.save(trainee);

        assertNotNull(savedTrainee);
        assertEquals(trainee.getUserId(), savedTrainee.getUserId());
    }

    @Test
    public void findTraineeByUsername() {

        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee savedTrainee = traineesRepository.save(trainee);

        assertEquals(traineesRepository.findByUsername("Gigi.Mirziashvili").orElse(new Trainee()), savedTrainee);
    }

    @Test
    public void findTraineeTrainingsWithContext() {

        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);

        Trainee trainee = new Trainee("TraineeFirst", "TraineeLast", "trainee_username",
                "password", true, "TraineeAddress", LocalDate.of(2002, 7, 18));
        Trainer trainer = new Trainer("TrainerFirst", "TrainerLast", "trainer_username", "password", true, trainingType);

        traineesRepository.save(trainee);
        trainersRepository.save(trainer);

        Training training = new Training(trainee, trainer, "Boxing session", trainingType,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));
        trainingsRepository.save(training);

        Set<Training> trainingList = traineesRepository.findTraineeTrainings(trainee.getUsername(),
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15),
                "trainer_username", trainingType);

        assertEquals(trainingList.size(), 1);
        assertTrue(trainingList.contains(training));
    }

    @Test
    public void findUnassignedTrainersWithTraineesUsername(){
        Trainee trainee = new Trainee("TraineeFirst", "TraineeLast", "trainee_username", "password", true, "TraineeAddress", LocalDate.of(2002, 7, 18));
        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);
        Trainer trainer1 = new Trainer("TrainerFirst1", "TrainerLast1", "trainer_username1", "password", true, trainingType);
        Trainer trainer2 = new Trainer("TrainerFirst2", "TrainerLast2", "trainer_username2", "password", true, trainingType);
        Training training = new Training(trainee, trainer1, "Boxing session", trainingType,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));

        traineesRepository.save(trainee);
        trainersRepository.save(trainer1);
        trainersRepository.save(trainer2);
        trainingsRepository.save(training);
        Set<Trainer> trainerList = traineesRepository.findUnassignedTrainers("trainee_username");

        assertEquals(trainerList.size(), 2);
        assertTrue(trainerList.contains(trainer2));
    }

}
