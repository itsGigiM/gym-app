package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainersRepository extends CrudRepository<Trainer, Long> {
    Optional<Trainer> findByUsername(String username);
}
