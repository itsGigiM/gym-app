package com.example.taskspring.dto;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GetTraineeTrainingListRequestDTO {
    private String username;
    private LocalDate from;
    private LocalDate to;
    private String trainerName;
    private TrainingType trainingType;
}
