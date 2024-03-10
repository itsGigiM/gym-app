package com.example.taskspring.dto.trainerDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostTrainerRequestDTO{
    private String firstName;

    private String lastName;

    private TrainingType specialization;
}
