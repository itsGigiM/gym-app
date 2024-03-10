package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingTypeDTO.GetTrainingTypesDTO;
import org.springframework.http.ResponseEntity;

public interface TrainingTypeController {
    public ResponseEntity<GetTrainingTypesDTO> getAll();
}
