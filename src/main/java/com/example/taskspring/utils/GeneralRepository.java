package com.example.taskspring.utils;

import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.repository.TrainingsRepository;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
@Data
public class GeneralRepository {
    private final TraineesRepository traineesRepository;
    private final TrainersRepository trainersRepository;
    private final TrainingsRepository trainingsRepository;

}