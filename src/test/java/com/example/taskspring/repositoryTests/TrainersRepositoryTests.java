package com.example.taskspring.repositoryTests;

import com.example.taskspring.model.*;
import com.example.taskspring.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TrainersRepositoryTests {
    @Autowired
    private TrainersRepository trainersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TraineesRepository traineesRepository;

    @Autowired
    private TrainingsRepository trainingsRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Test
    public void addTrainerAndRetrieveItFromRepository() {
        User user = new User("Gigi", "Mirziashvili", "Gigilo.Mirziashvili",
                "password", true);
        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);
        Trainer trainee = new Trainer(user, trainingType);

        usersRepository.save(trainee.getUser());
        Trainer savedTrainer = trainersRepository.save(trainee);

        assertNotNull(savedTrainer);
        assertEquals(trainee.getTrainerId(), savedTrainer.getTrainerId());
    }

    @Test
    public void findTrainerTrainingsWithContext() {

        User traineeUser = new User("TraineeFirst", "TraineeLast", "trainee_username", "password", true);
        User trainerUser = new User("TrainerFirst", "TrainerLast", "trainer_username", "password", true);
        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);
        Trainee trainee = new Trainee(traineeUser, "TraineeAddress", LocalDate.of(2002, 7, 18));
        Trainer trainer = new Trainer(trainerUser, trainingType);
        Training training = new Training(trainee, trainer, "Boxing session", trainingType,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));

        usersRepository.save(traineeUser);
        usersRepository.save(trainerUser);
        traineesRepository.save(trainee);
        trainersRepository.save(trainer);
        trainingsRepository.save(training);
        List<Training> trainingList = trainersRepository.findTrainerTrainings(trainer.getUser().getUsername(),
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 15),
                "trainee_username");

        assertEquals(trainingList.size(), 1);
        assertEquals(trainingList.get(0), training);
    }
    @Test
    public void findUnassignedTrainersWithTraineesUsername(){
        User traineeUser = new User("TraineeFirst", "TraineeLast", "trainee_username", "password", true);
        User trainerUser1 = new User("TrainerFirst1", "TrainerLast1", "trainer_username1", "password", true);
        User trainerUser2 = new User("TrainerFirst2", "TrainerLast2", "trainer_username2", "password", true);
        Trainee trainee = new Trainee(traineeUser, "TraineeAddress", LocalDate.of(2002, 7, 18));
        TrainingType trainingType = trainingTypeRepository.getTrainingTypeByTrainingTypeName(TrainingTypeEnum.BOXING);
        Trainer trainer1 = new Trainer(trainerUser1, trainingType);
        Trainer trainer2 = new Trainer(trainerUser2, trainingType);
        Training training = new Training(trainee, trainer1, "Boxing session", trainingType,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));

        usersRepository.save(traineeUser);
        usersRepository.save(trainerUser1);
        usersRepository.save(trainerUser2);
        traineesRepository.save(trainee);
        trainersRepository.save(trainer1);
        trainersRepository.save(trainer2);
        trainingsRepository.save(training);
        List<Trainer> trainerList = trainersRepository.findUnassignedTrainers("trainee_username");

        assertEquals(trainerList.size(), 1);
        assertEquals(trainerList.get(0), trainer2);


    }

}
