package com.example.taskspring;

import model.*;
import org.junit.jupiter.api.*;
import repository.TraineesInMemoryDAO;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TraineesInMemoryDAOTests {
    private TraineesInMemoryDAO repo;

    @BeforeEach
    public void setUpRepository() {
        repo = new TraineesInMemoryDAO();
    }
    @Test
    public void testAddAndGet() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "1033", "Tbilisi", new Date());
        repo.add(trainee);

        assertTrue(repo.exists("1033"));
        assertEquals(trainee, repo.get("1033"));
    }

    @Test
    public void testRemove() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "1033", "Tbilisi", new Date());
        repo.add(trainee);
        assertTrue(repo.exists("1033"));

        repo.remove("1033");
        assertFalse(repo.exists("1033"));
    }

    @Test
    public void testSet() {
        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "1033", "Tbilisi", new Date());
        repo.add(trainee);
        trainee.setFirstName("Epam");
        repo.set(trainee);
        assertSame("Epam", repo.get("1033").getFirstName());
    }

    @Test
    public void testGetAll() {
        Trainee trainee1 = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "1033", "Tbilisi", new Date());

        Trainee trainee2 = new Trainee("Gigi", "Epam", "Gigi.Epam",
                "password", true, "1034", "Tbilisi", new Date());

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
                "password", true, "1033", "Tbilisi", new Date(2024, 1, 1));
        repo.add(trainee);

        assertEquals("{1033=Trainee(userId=1033, address=Tbilisi, dateOfBirth=Fri Feb 01 00:00:00 GET 3924)}", repo.toString());


    }
}
