package com.example.taskspring.repository.repositories;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
