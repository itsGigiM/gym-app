package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.dto.trainerDTO.BasicTrainerDTO;
import com.example.taskspring.model.Trainer;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class GetTraineeResponseDTO {
    private String firstName;

    private String lastName;

    private boolean isActive;

    private String address;

    private LocalDate dateOfBirth;

    private Set<BasicTrainerDTO> trainers;

}
