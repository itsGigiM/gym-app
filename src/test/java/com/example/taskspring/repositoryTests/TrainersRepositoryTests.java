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
public class TrainersRepositoryTests {
    @Autowired
    private TrainersRepository trainersRepository;

    @Autowired
    private TraineesRepository traineesRepository;

    @Autowired
    private TrainingsRepository trainingsRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Test
    public void addTrainerAndRetrieveItFromRepository() {
        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);
        Trainer trainee = new Trainer("Gigi", "Mirziashvili", "Gigilo.Mirziashvili",
                "password", true, trainingType);

        Trainer savedTrainer = trainersRepository.save(trainee);

        assertNotNull(savedTrainer);
        assertEquals(trainee.getUserId(), savedTrainer.getUserId());
    }

    @Test
    public void findTrainerTrainingsWithContext() {

        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);
        Trainee trainee = new Trainee("TraineeFirst", "TraineeLast", "trainee_username",
                "password", true, "TraineeAddress", LocalDate.of(2002, 7, 18));
        Trainer trainer = new Trainer("TrainerFirst", "TrainerLast", "trainer_username", "password", true, trainingType);
        Training training = new Training(trainee, trainer, "Boxing session", trainingType,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));

        traineesRepository.save(trainee);
        trainersRepository.save(trainer);
        trainingsRepository.save(training);
        Set<Training> trainingList = trainersRepository.findTrainerTrainings(trainer.getUsername(),
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15),
                "trainee_username");

        assertEquals(trainingList.size(), 1);
        assertTrue(trainingList.contains(training));
    }

}
