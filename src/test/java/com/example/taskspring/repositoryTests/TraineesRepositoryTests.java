package com.example.taskspring.repositoryTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {com.example.taskspring.TaskSpringApplication.class})
public class TraineesRepositoryTests {
    @Autowired
    private TraineesRepository repo;

    @Test
    public void testAddAndGet() {
        Trainee trainee = new Trainee("Gigilo", "Mirziashvili", "Gigilo.Mirziashvili",
                "password", true, 900L, "Tbilisi", LocalDate.of(2022, 2, 2));
        repo.save(trainee);

        assertTrue(repo.findByUsername("Gigilo.Mirziashvili").isPresent());
        assertEquals(trainee, repo.findById(900L).orElse(new Trainee()));
    }
//
//    @Test
//    public void testRemove() {
//        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
//                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
//        repo.save(trainee);
//        assertTrue(repo.findById(1033L).isPresent());
//
//        repo.deleteById(1033L);
//        assertFalse(repo.findById(1033L).isPresent());
//    }
//
//    @Test
//    public void testSet() {
//        Trainee trainee = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
//                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
//        repo.save(trainee);
//        trainee.setFirstName("Epam");
//        repo.save(trainee);
//        assertSame("Epam", repo.findById(1033L).orElse(trainee).getFirstName());
//    }
//
//    @Test
//    public void testGetAll() {
//        Trainee trainee1 = new Trainee("Gigi", "Mirziashvili", "Gigi.Mirziashvili",
//                "password", true, 1033L, "Tbilisi", LocalDate.of(2022, 2, 2));
//
//        Trainee trainee2 = new Trainee("Gigi", "Epam", "Gigi.Epam",
//                "password", true, 1034L, "Tbilisi", LocalDate.of(2022, 2, 2));
//
//        repo.save(trainee1);
//        repo.save(trainee2);
//        List<Trainee> l = (List<Trainee>) repo.findAll();
//
//        assertTrue(l.contains(trainee1));
//        assertTrue(l.contains(trainee2));
//        assertEquals(l.size(), 2);
//
//    }
}
