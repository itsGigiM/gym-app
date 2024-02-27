package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineesRepository extends CrudRepository<Trainee, Long> {
    Optional<Trainee> findByUsername(String username);
}
