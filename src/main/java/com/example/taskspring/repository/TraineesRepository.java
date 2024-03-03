package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TraineesRepository extends CrudRepository<Trainee, Long> {
    Optional<Trainee> findByUserUsername(String username);

    @Query("SELECT t FROM Training t WHERE t.trainee.user.username = :traineeUsername " +
            "AND (:fromDate IS NULL OR t.trainingDate >= :fromDate) " +
            "AND (:toDate IS NULL OR t.trainingDate <= :toDate) " +
            "AND (:trainerName IS NULL OR t.trainer.user.username = :trainerName) " +
            "AND (:trainingType IS NULL OR t.trainingType = :trainingType)")
    List<Training> findTraineeTrainings(
            @Param("traineeUsername") String traineeUsername,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("trainerName") String trainerName,
            @Param("trainingType") TrainingType.TrainingTypeEnum trainingType
    );
}
