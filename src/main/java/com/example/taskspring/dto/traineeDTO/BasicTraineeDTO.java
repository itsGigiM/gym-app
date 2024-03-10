package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BasicTraineeDTO {
    private String username;
    private String firstName;
    private String lastName;
}
