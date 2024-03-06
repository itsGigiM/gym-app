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

        assertEquals("Gigi", trainee.getFirstName());
        assertEquals("Mirziashvili", trainee.getLastName());
        assertEquals("Gigi.Mirziashvili", trainee.getUsername());
        trainee.setFirstName("Gigi2");
        assertEquals("Gigi2", trainee.getFirstName());
        assertEquals(password, trainee.getPassword());
        assertTrue(trainee.isActive());
        assertEquals(trainee.getTrainers().size(), 0);
        assertEquals(trainee.getTrainings().size(), 0);
        assertEquals(10L, trainee.getUserId());
        trainee.setUserId(15L);
        assertEquals(15L, trainee.getUserId());
        assertEquals("Tbilisi", trainee.getAddress());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertNotNull(trainee.toString());
    }

    @Test
    public void testTrainer() {
        String password = "a".repeat(10);
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        Trainer trainer = new Trainer(10L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, trainingType);

        assertEquals(trainer.getUserId(), 10L);
        assertEquals("Gigi", trainer.getFirstName());
        trainer.setFirstName("Gigi2");
        assertEquals("Gigi2", trainer.getFirstName());
        assertEquals("Mirziashvili", trainer.getLastName());
        assertEquals("Gigi.Mirziashvili", trainer.getUsername());
        assertEquals(password, trainer.getPassword());
        assertEquals(trainer.getTrainees().size(), 0);
        assertTrue(trainer.isActive());
        assertEquals(trainingType, trainer.getSpecialization());
        assertEquals(10L, trainer.getUserId());
        trainer.setUserId(15L);
        assertEquals(15L, trainer.getUserId());
        assertNotNull(trainer.toString());
    }

    @Test
    public void testTraining() {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        Trainer trainer = new Trainer(10L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, trainingType);
        Trainee trainee = new Trainee(11L, "Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Tbilisi", LocalDate.of(2022, 2, 2));
        Training training = new Training(12L, trainee, trainer,
                "Box", trainingType, LocalDate.of(2022, 2, 2), Duration.ofMinutes(30));

        Training nullTraining = new Training();
        assertEquals(12L, training.getTrainingId());
        assertEquals(11L, training.getTrainee().getUserId());
        assertEquals(10L, training.getTrainer().getUserId());
        assertEquals("Box", training.getTrainingName());
        assertEquals(trainingType, training.getTrainingType());
        assertEquals(training.getTrainingDate(), LocalDate.of(2022, 2, 2));
        assertEquals(Duration.ofMinutes(30), training.getDuration());
        assertNull(nullTraining.getTrainingName());
    }
}
