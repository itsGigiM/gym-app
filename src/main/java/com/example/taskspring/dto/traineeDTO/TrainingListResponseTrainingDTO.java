package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TrainingListResponseTrainingDTO {
    private String trainingName;
    private LocalDate trainingDate;
    private TrainingType trainingType;
    private Duration duration;
    private String trainerName;
}
