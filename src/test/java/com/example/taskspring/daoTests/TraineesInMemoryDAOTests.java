package com.example.taskspring.daoTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.InMemoryStorage;
import org.junit.jupiter.api.*;
import com.example.taskspring.repository.TraineesInMemoryDAO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TraineesInMemoryDAOTests {
    private TraineesInMemoryDAO repo;

    @BeforeEach
    public void setUpRepository() {
        InMemoryStorage memo = new InMemoryStorage();
        repo = new TraineesInMemoryDAO(memo);
    }
    @Test
    public void addTraineeToRepositoryAndRetrieveIt() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
        repo.add(trainee);

        assertTrue(repo.exists(1033L));
        assertEquals(trainee, repo.get(1033L));
    }

    @Test
    public void removeTraineeFromRepository() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
        repo.add(trainee);
        assertTrue(repo.exists(1033L));

        repo.remove(1033L);
        assertFalse(repo.exists(1033L));
    }

    @Test
    public void setExistingTrainersName() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
        repo.add(trainee);
        trainee.setFirstName("Epam");
        repo.set(trainee);
        assertSame("Epam", repo.get(1033L).getFirstName());
    }

    @Test
    public void getAllTraineesFromRepository() {
        Trainee trainee1 = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));

        Trainee trainee2 = new Trainee("Gigi", "Epam", "Gigi.Epam",
                "password", true, 1034L, "Tbilisi", LocalDate.of(2022, 2, 2));

        repo.add(trainee1);
        repo.add(trainee2);
        List<Trainee> l = repo.getAll();

        assertTrue(l.contains(trainee1));
        assertTrue(l.contains(trainee2));
        assertEquals(l.size(), 2);

    }

    @Test
    public void testToString() {
        assertSame("{}", repo.toString());

        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
        repo.add(trainee);

        assertEquals("{1033=Trainee{firstName='Gigi', lastName='Mirziashvili', username=Gigi.Mirziashvili, isActive=true, traineeId='1033', address='Tbilisi', dateOfBirth=2022-02-02} }"
                , repo.toString());


    }
}
