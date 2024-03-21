package com.example.taskspring.dto.trainingDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostTrainingRequest {
    @NonNull
    private String traineeUsername;
    @NonNull
    private String trainerUsername;
    @NonNull
    private String trainingName;
    @NonNull
    private LocalDate trainingDate;
    @NonNull
    private Duration trainingDuration;

}
