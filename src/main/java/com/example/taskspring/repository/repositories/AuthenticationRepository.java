package com.example.taskspring.repository.repositories;

import com.example.taskspring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends CrudRepository<User, Long> {

}
