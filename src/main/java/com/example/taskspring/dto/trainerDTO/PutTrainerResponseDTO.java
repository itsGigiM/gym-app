package com.example.taskspring.dto.trainerDTO;

import com.example.taskspring.dto.traineeDTO.BasicTraineeDTO;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class PutTrainerResponseDTO {
    private String username;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private TrainingType specialization;

    private Set<BasicTraineeDTO> trainees;

}
