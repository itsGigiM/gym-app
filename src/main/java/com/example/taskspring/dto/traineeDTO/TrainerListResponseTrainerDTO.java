package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainerListResponseTrainerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}
