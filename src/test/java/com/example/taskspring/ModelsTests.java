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

        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, 1033L, "Tbilisi", dateOfBirth);

        Trainee nullTrainee = new Trainee();

        assertEquals("Gigi", trainee.getFirstName());
        assertEquals("Mirziashvili", trainee.getLastName());
        assertEquals("Gigi.Mirziashvili", trainee.getUsername());
        assertEquals(password, trainee.getPassword());
        assertTrue(trainee.isActive());
        assertEquals(1033L, trainee.getTraineeId());
        assertEquals("Tbilisi", trainee.getAddress());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertNull(nullTrainee.getUsername());
        assertNotSame("", trainee.toString());
        assertNotSame(-1, trainee.hashCode());
    }

    @Test
    public void testTrainer() {
        String password = "a".repeat(10);
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, "Box", 12345L);

        Trainer nullTrainer = new Trainer();

        assertEquals("Gigi", trainer.getFirstName());
        assertEquals("Mirziashvili", trainer.getLastName());
        assertEquals("Gigi.Mirziashvili", trainer.getUsername());
        assertEquals(password, trainer.getPassword());
        assertTrue(trainer.isActive());
        assertEquals("Box", trainer.getSpecialization());
        assertEquals(12345L, trainer.getTrainerId());
        assertNull(nullTrainer.getUsername());
        assertNotSame("", trainer.toString());
        assertNotSame(-1, trainer.hashCode());
    }

    @Test
    public void testTraining() {
        Training training = new Training(1000L, 12345L, 67890L,
                "Box", TrainingType.BOXING, LocalDate.of(2022, 2, 2), Duration.ofMinutes(30));

        Training nullTraining = new Training();
        assertEquals(1000L, training.getTrainingId());
        assertEquals(12345L, training.getTraineeId());
        assertEquals(67890L, training.getTrainerId());
        assertEquals("Box", training.getTrainingName());
        assertEquals(TrainingType.BOXING, training.getTrainingType());
        assertEquals(training.getTrainingDate(), LocalDate.of(2022, 2, 2));
        assertEquals(Duration.ofMinutes(30), training.getDuration());
        assertNull(nullTraining.getTrainingName());
        assertNotSame("", training.toString());

    }
}
