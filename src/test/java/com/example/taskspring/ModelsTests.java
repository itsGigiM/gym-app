package com.example.taskspring;

import com.example.taskspring.model.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ModelsTests {
    @Test
    public void testTrainee() {
        String password = "a".repeat(10);
        LocalDate dateOfBirth = LocalDate.of(2022, 2, 2);

        Trainee trainee = new Trainee(10L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, "Tbilisi", dateOfBirth);

        Trainee secondTrainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, "Tbilisi", dateOfBirth);

        Trainee nullTrainee = new Trainee();

        assertEquals("Gigi", trainee.getUser().getFirstName());
        assertEquals("Mirziashvili", trainee.getUser().getLastName());
        assertEquals("Gigi.Mirziashvili", trainee.getUser().getUsername());
        trainee.getUser().setFirstName("Gigi2");
        assertEquals("Gigi2", trainee.getUser().getFirstName());
        assertEquals(password, trainee.getUser().getPassword());
        assertTrue(trainee.getUser().isActive());
        assertEquals(trainee.getTrainers().size(), 0);
        assertEquals(trainee.getTrainings().size(), 0);
        assertEquals(10L, trainee.getTraineeId());
        trainee.setTraineeId(15L);
        assertEquals(15L, trainee.getTraineeId());
        assertEquals("Tbilisi", trainee.getAddress());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertNotNull(trainee.toString());
        assertNull(nullTrainee.getUser());
        assertNotNull(secondTrainee.getUser());
    }

    @Test
    public void testTrainer() {
        String password = "a".repeat(10);
        Trainer trainer = new Trainer(10L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, TrainingType.TrainingTypeEnum.BOXING);

        Trainer secondTrainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, TrainingType.TrainingTypeEnum.BOXING);
        User user = new User("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true);
        User user2 = new User("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, 2L);
        Trainer thirdTrainer = new Trainer(user, TrainingType.TrainingTypeEnum.BOXING);

        Trainer nullTrainer = new Trainer();

        assertEquals(user2.getId(), 2L);

        assertEquals("Gigi", trainer.getUser().getFirstName());
        trainer.getUser().setFirstName("Gigi2");
        assertEquals("Gigi2", trainer.getUser().getFirstName());
        assertEquals("Mirziashvili", trainer.getUser().getLastName());
        assertEquals("Gigi.Mirziashvili", trainer.getUser().getUsername());
        assertEquals(password, trainer.getUser().getPassword());
        assertEquals(trainer.getTrainees().size(), 0);
        assertTrue(trainer.getUser().isActive());
        assertEquals(TrainingType.TrainingTypeEnum.BOXING, trainer.getSpecialization());
        assertEquals(10L, trainer.getTrainerId());
        trainer.setTrainerId(15L);
        assertEquals(15L, trainer.getTrainerId());
        assertNotNull(trainer.toString());
        assertNull(nullTrainer.getUser());
        assertNotNull(secondTrainer.getUser());
        assertNotNull(thirdTrainer.getUser());
    }

    @Test
    public void testTraining() {
        Trainer trainer = new Trainer(10L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, TrainingType.TrainingTypeEnum.BOXING);
        Trainee trainee = new Trainee(11L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Tbilisi", LocalDate.of(2022, 2, 2));
        Training training = new Training(12L, trainee, trainer,
                "Box", TrainingType.TrainingTypeEnum.BOXING, LocalDate.of(2022, 2, 2), Duration.ofMinutes(30));

        Training nullTraining = new Training();
        assertEquals(12L, training.getTrainingId());
        assertEquals(11L, training.getTrainee().getTraineeId());
        assertEquals(10L, training.getTrainer().getTrainerId());
        assertEquals("Box", training.getTrainingName());
        assertEquals(TrainingType.TrainingTypeEnum.BOXING, training.getTrainingType());
        assertEquals(training.getTrainingDate(), LocalDate.of(2022, 2, 2));
        assertEquals(Duration.ofMinutes(30), training.getDuration());
        assertNull(nullTraining.getTrainingName());
    }
}
