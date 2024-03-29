package com.example.taskspring.utilsTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.TraineesInMemoryDAO;
import com.example.taskspring.repository.TrainersInMemoryDAO;
import com.example.taskspring.repository.InMemoryStorage;
import com.example.taskspring.utils.UsernameGeneratorImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsernameGeneratorImplTests {

    private final UsernameGeneratorImpl usernameGeneratorImpl;
    private final TraineesInMemoryDAO traineesInMemoryDAO;
    private final TrainersInMemoryDAO trainersInMemoryDAO;



    public UsernameGeneratorImplTests() {
        InMemoryStorage memoryStorage = new InMemoryStorage();
        traineesInMemoryDAO = new TraineesInMemoryDAO(memoryStorage);
        trainersInMemoryDAO = new TrainersInMemoryDAO(memoryStorage);
        this.usernameGeneratorImpl = new UsernameGeneratorImpl(traineesInMemoryDAO, trainersInMemoryDAO);
    }

    @Test
    public void generateUsername(){
        String username = usernameGeneratorImpl.generateUsername("John", "Doe");
        assertEquals(username, "John.Doe");
    }

    @Test
    public void generateUsernameWithSuffix(){
        traineesInMemoryDAO.add(new Trainee("John", "Doe", "John.Doe",
                "password", true, 2000L, "Tbilisi", LocalDate.of(2005, 10, 10)));
        String username = usernameGeneratorImpl.generateUsername("John", "Doe");
        assertEquals(username, "John.Doe1");
        traineesInMemoryDAO.add(new Trainee("John", "Doe", "John.Doe1",
                "password", true, 2001L, "Tbilisi", LocalDate.of(2005, 10, 10)));
        trainersInMemoryDAO.add(new Trainer("John", "Doe", "John.Doe2", "password", true, TrainingType.BOXING, 650L));
        String secondUsername = usernameGeneratorImpl.generateUsername("John", "Doe");
        assertEquals(secondUsername, "John.Doe3");
    }
}
