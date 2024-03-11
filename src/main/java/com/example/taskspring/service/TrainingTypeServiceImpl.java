package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.TrainingTypeRepository;
import com.example.taskspring.repository.repositories.TrainingsRepository;
import com.example.taskspring.utils.PasswordGenerator;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@NoArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService{
    private TrainingTypeRepository repository;

    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeRepository repository) {
        this.repository = repository;
    }

    public Set<TrainingType> getAll(){
        Set<TrainingType> trainingTypes = repository.findAll();
        log.info("retrieved all training types");
        return trainingTypes;
    }

    public TrainingType getById(Long id){
        TrainingType trainingType = repository.findTrainingTypeById(id);
        log.info("retrieved training type {}", trainingType);
        return trainingType;
    }
}
