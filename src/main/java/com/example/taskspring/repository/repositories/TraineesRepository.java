package com.example.taskspring.repository.repositories;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TraineesRepository extends CrudRepository<Trainee, Long> {
    Optional<Trainee> findByUsername(String username);

    @Query("SELECT t FROM Training t WHERE t.trainee.username = :traineeUsername " +
            "AND (:fromDate IS NULL OR t.trainingDate >= :fromDate) " +
            "AND (:toDate IS NULL OR t.trainingDate <= :toDate) " +
            "AND (:trainerName IS NULL OR t.trainer.username = :trainerName) " +
            "AND (:trainingType IS NULL OR t.trainingType = :trainingType)")
    Set<Training> findTraineeTrainings(
            @Param("traineeUsername") String traineeUsername,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("trainerName") String trainerName,
            @Param("trainingType") TrainingType trainingType
    );

    @Query("SELECT DISTINCT t FROM Trainer t " +
            "WHERE t NOT IN (SELECT DISTINCT tr.trainer FROM Training tr WHERE tr.trainee.username = :traineeUsername)")
    Set<Trainer> findUnassignedTrainers(@Param("traineeUsername") String traineeUsername);
}
