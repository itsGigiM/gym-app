package com.example.taskspring.dto.trainerDTO;

import com.example.taskspring.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GetTrainerTrainingListRequestDTO {
    @NonNull
    private String username;
    private LocalDate from;
    private LocalDate to;
    private String traineeName;
}

