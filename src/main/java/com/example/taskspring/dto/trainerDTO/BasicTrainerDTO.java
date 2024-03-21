package com.example.taskspring.dto.trainerDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BasicTrainerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}
