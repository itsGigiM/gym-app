package com.example.taskspring;

import model.*;
import org.junit.jupiter.api.*;
import repository.TrainersInMemoryDAO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainersInMemoryDAOTests {
    private TrainersInMemoryDAO repo;

    @BeforeEach
    public void setUpRepository() {
        repo = new TrainersInMemoryDAO();
    }
    @Test
    public void testAddAndGet() {
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Box", "1033");
        repo.add(trainer);

        assertTrue(repo.exists("1033"));
        assertEquals(trainer, repo.get("1033"));
    }

    @Test
    public void testSet() {
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Box", "1033");
        repo.add(trainer);
        trainer.setFirstName("Epam");
        repo.set(trainer);
        assertSame("Epam", repo.get("1033").getFirstName());
    }

    @Test
    public void testGetAll() {
        Trainer trainer1 = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "Box", "1033");

        Trainer trainer2 = new Trainer("Gigi", "Epam", "Gigi.Mirziashvili",
                "password", true, "Box", "1034");

        repo.add(trainer1);
        repo.add(trainer2);
        List<Trainer> l = repo.getAll();

        assertTrue(l.contains(trainer1));
        assertTrue(l.contains(trainer2));
        assertEquals(l.size(), 2);

    }

    @Test
    public void testToString() {
        assertSame("{}", repo.toString());

        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, "BOX", "1033");
        repo.add(trainer);

        assertEquals("{1033=Trainer(specialization=BOX, userId=1033)}", repo.toString());


    }
}
