package com.example.taskspring.utils;

import com.example.taskspring.model.TrainingType;
import com.example.taskspring.model.TrainingTypeEnum;
import com.example.taskspring.repository.TrainingTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SQLMemoryInitialization {
    private TrainingTypeRepository trainingTypeRepository;

    @PostConstruct
    public void init() {
        if (trainingTypeRepository.count() == 0) {
            TrainingType boxing = new TrainingType(1L, TrainingTypeEnum.BOXING);
            trainingTypeRepository.save(boxing);

            TrainingType jiujitsu = new TrainingType(2L, TrainingTypeEnum.JIUJITSU);
            trainingTypeRepository.save(jiujitsu);

            TrainingType karate = new TrainingType(3L, TrainingTypeEnum.KARATE);
            trainingTypeRepository.save(karate);
        }
    }
}
