package com.example.taskspring.repository;

import com.example.taskspring.model.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingsRepository extends CrudRepository<Training, Long> {
}
