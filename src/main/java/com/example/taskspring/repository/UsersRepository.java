package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
