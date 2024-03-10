package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class BasicTraineeDTO {
    private String username;
    private String firstName;
    private String lastName;
}
