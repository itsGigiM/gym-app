package com.example.taskspring.daoTests;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.InMemoryStorage;
import org.junit.jupiter.api.*;
import com.example.taskspring.repository.TrainersInMemoryDAO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainersInMemoryDAOTests {
    private TrainersInMemoryDAO repo;

    @BeforeEach
    public void setUpRepository() {
        InMemoryStorage memo = new InMemoryStorage();
        repo = new TrainersInMemoryDAO(memo);
    }
    @Test
    public void testAddAndGet() {
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, TrainingType.BOXING, 1033L);
        repo.add(trainer);

        assertTrue(repo.exists(1033L));
        assertEquals(trainer, repo.get(1033L));
    }

    @Test
    public void testSet() {
        Trainer trainer = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, TrainingType.BOXING, 1033L);
        repo.add(trainer);
        trainer.setFirstName("Epam");
        repo.set(trainer);
        assertSame("Epam", repo.get(1033L).getFirstName());
    }

    @Test
    public void testGetAll() {
        Trainer trainer1 = new Trainer("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
                "password", true, TrainingType.BOXING, 1033L);

        Trainer trainer2 = new Trainer("Gigi", "Epam", "Gigi.Mirziashvili",
                "password", true, TrainingType.BOXING, 1034L);

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
                "password", true, TrainingType.BOXING, 1033L);
        repo.add(trainer);

        assertEquals("{1033=Trainee{firstName='Gigi', lastName='Mirziashvili', username=Gigi.Mirziashvili, password=password, isActive=true, specialization='BOXING', trainerId='1033'} }",
                repo.toString());


    }
}
