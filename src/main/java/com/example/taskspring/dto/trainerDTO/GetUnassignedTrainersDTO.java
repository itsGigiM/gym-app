package com.example.taskspring.dto.trainerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetUnassignedTrainersDTO {
    private Set<BasicTrainerDTO> basicTrainerDTOS;
}
