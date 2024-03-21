package com.example.taskspring.dto.trainingTypeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetTrainingTypesDTO {
    Set<BasicTrainingTypeDTO> trainingTypeDTOSet;
}
