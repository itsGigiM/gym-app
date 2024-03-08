package com.example.taskspring.repository.repositories;

import com.example.taskspring.model.TrainingType;
import com.example.taskspring.model.TrainingTypeEnum;
import org.springframework.data.repository.CrudRepository;

public interface TrainingTypeRepository extends CrudRepository<TrainingType, Long> {
    public TrainingType getTrainingTypeByTrainingTypeName(TrainingTypeEnum trainingTypeEnum);
}
