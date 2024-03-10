package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class PutTraineeRequestDTO {
    private String username;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private String address;

    private LocalDate dateOfBirth;
}
