package com.example.taskspring.repository.repositories;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TrainersRepository extends CrudRepository<Trainer, Long> {
    Optional<Trainer> findByUsername(String username);

    @Query("SELECT t FROM Training t WHERE t.trainer.username = :trainerUsername " +
            "AND (:fromDate IS NULL OR t.trainingDate >= :fromDate) " +
            "AND (:toDate IS NULL OR t.trainingDate <= :toDate) " +
            "AND (:traineeName IS NULL OR t.trainee.username = :traineeName)")
    Set<Training> findTrainerTrainings(
            @Param("trainerUsername") String trainerUsername,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("traineeName") String traineeName
    );
}
