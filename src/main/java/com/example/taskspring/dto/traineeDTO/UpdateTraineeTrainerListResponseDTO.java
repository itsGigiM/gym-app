package com.example.taskspring.dto.traineeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UpdateTraineeTrainerListResponseDTO {
    Set<TrainerListResponseTrainerDTO> trainersList;
}

