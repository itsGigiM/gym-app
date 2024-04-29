package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class PutTraineeRequestDTO {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private boolean isActive;
    @NonNull
    private String address;

    private LocalDate dateOfBirth;
}
