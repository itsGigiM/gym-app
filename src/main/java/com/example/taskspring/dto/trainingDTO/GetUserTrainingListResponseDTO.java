package com.example.taskspring.dto.trainingDTO;

import com.example.taskspring.dto.traineeDTO.TrainingListResponseTrainingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetUserTrainingListResponseDTO {
    Set<TrainingListResponseTrainingDTO> trainingList;
}
