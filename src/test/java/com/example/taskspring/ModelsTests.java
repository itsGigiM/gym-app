package com.example.taskspring;

import model.*;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Constants.PASSWORD_LENGTH;

public class ModelsTest {

    @Test
    public void testUser() {
        User user = new User("Gigi", "Mirziashvili", "Gigi.Mirziashvili" +
                "", "password", true);

        assertEquals("Gigi", user.getFirstName());
        assertEquals("Mirziashvili", user.getLastName());
        assertEquals("Gigi.Mirziashvili", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.isActive());
    }
    @Test
    public void testTrainee() {
        String firstName = "Gigi";
        String lastName = "Mirziashvili";
        String username = "Gigi.Mirziashvili";
        String password = "a".repeat(PASSWORD_LENGTH);
        boolean isActive = true;
        String userId = "1033";
        String address = "Tbilisi";
        Date dateOfBirth = new Date(2023, 2, 2);

        Trainee trainee = new Trainee(firstName, lastName, username, password, isActive, userId, address, dateOfBirth);

        assertEquals(firstName, trainee.getFirstName());
        assertEquals(lastName, trainee.getLastName());
        assertEquals(username, trainee.getUsername());
        assertEquals(password, trainee.getPassword());
        assertTrue(trainee.isActive());
        assertEquals(userId, trainee.getUserId());
        assertEquals(address, trainee.getAddress());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
    }

    @Test
    public void testTrainer() {
        String password = "a".repeat(PASSWORD_LENGTH);
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                password, true, "Box", "12345");

        assertEquals("Gigi", trainer.getFirstName());
        assertEquals("Mirziashvili", trainer.getLastName());
        assertEquals("Gigi.Mirziashvili", trainer.getUsername());
        assertEquals(password, trainer.getPassword());
        assertTrue(trainer.isActive());
        assertEquals("Box", trainer.getSpecialization());
        assertEquals("12345", trainer.getUserId());
    }

    @Test
    public void testTraining() {
        Training training = new Training("12345", "67890",
                "Box", TrainingType.BOXING, new Date(2024, Calendar.MARCH, 30), Duration.ofMinutes(30));

        assertEquals("12345", training.getTraineeId());
        assertEquals("67890", training.getTrainerId());
        assertEquals("Box", training.getTrainingName());
        assertEquals(TrainingType.BOXING, training.getTrainingType());
        assertEquals(training.getTrainingDate(), new Date(2024, Calendar.MARCH, 30));
        assertEquals(Duration.ofMinutes(30), training.getDuration());
    }
}
