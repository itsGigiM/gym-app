package com.example.taskspring.repositoryTests;

import com.example.taskspring.model.*;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.repository.TrainingsRepository;
import com.example.taskspring.repository.UsersRepository;
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

    @Test
    public void addTrainerAndRetrieveItFromRepository() {
        User user = new User("Gigi", "Mirziashvili", "Gigilo.Mirziashvili",
                "password", true);

        Trainer trainee = new Trainer(user, TrainingType.BOXING);

        usersRepository.save(trainee.getUser());

        Trainer savedTrainer = trainersRepository.save(trainee);

        assertNotNull(savedTrainer);
        assertEquals(trainee.getTrainerId(), savedTrainer.getTrainerId());
    }

    @Test
    public void findTrainerTrainingsWithContext() {

        User traineeUser = new User("TraineeFirst", "TraineeLast", "trainee_username", "password", true);
        User trainerUser = new User("TrainerFirst", "TrainerLast", "trainer_username", "password", true);

        usersRepository.save(traineeUser);
        usersRepository.save(trainerUser);

        Trainee trainee = new Trainee(traineeUser, "TraineeAddress", LocalDate.of(2002, 7, 18));
        Trainer trainer = new Trainer(trainerUser, TrainingType.BOXING);

        traineesRepository.save(trainee);
        trainersRepository.save(trainer);

        Training training = new Training(trainee, trainer, "Boxing session", TrainingType.BOXING,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));
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

        usersRepository.save(traineeUser);
        usersRepository.save(trainerUser1);
        usersRepository.save(trainerUser2);

        Trainee trainee = new Trainee(traineeUser, "TraineeAddress", LocalDate.of(2002, 7, 18));
        Trainer trainer1 = new Trainer(trainerUser1, TrainingType.BOXING);
        Trainer trainer2 = new Trainer(trainerUser2, TrainingType.BOXING);

        traineesRepository.save(trainee);
        trainersRepository.save(trainer1);
        trainersRepository.save(trainer2);

        Training training = new Training(trainee, trainer1, "Boxing session", TrainingType.BOXING,
                LocalDate.of(2024, 1, 10), Duration.ofHours(1));
        trainingsRepository.save(training);

        List<Trainer> trainerList = trainersRepository.findUnassignedTrainers("trainee_username");

        assertEquals(trainerList.size(), 1);
        assertEquals(trainerList.get(0), trainer2);


    }

}
