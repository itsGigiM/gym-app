package com.example.taskspring.dto.trainerDTO;

import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PutTrainerRequestDTO {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private boolean isActive;
    @NonNull
    private Long specializationId;

}
