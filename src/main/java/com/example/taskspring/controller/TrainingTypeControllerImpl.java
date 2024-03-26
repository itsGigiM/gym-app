package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingTypeDTO.BasicTrainingTypeDTO;
import com.example.taskspring.dto.trainingTypeDTO.GetTrainingTypesDTO;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.service.TrainingTypeService;
import com.example.taskspring.actuator.metric.TrainingTypeMetrics;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/training-types")
@Slf4j
@NoArgsConstructor
public class TrainingTypeControllerImpl implements TrainingTypeController {

    private TrainingTypeService trainingTypeService;
    private TrainingTypeMetrics trainingTypeMetrics;
    @Autowired
    public TrainingTypeControllerImpl(TrainingTypeService trainingTypeService, TrainingTypeMetrics trainingTypeMetrics) {
        this.trainingTypeService = trainingTypeService;
        this.trainingTypeMetrics = trainingTypeMetrics;
    }

    @GetMapping()
    public ResponseEntity<GetTrainingTypesDTO> getAll(){
        log.info("Received GET request to retrieve all training types.");
        Set<TrainingType> trainingTypes = trainingTypeService.getAll();
        Set<BasicTrainingTypeDTO> basicTrainingTypeDTOs = new HashSet<>();
        for (TrainingType t : trainingTypes) {
            basicTrainingTypeDTOs.add(new BasicTrainingTypeDTO(t.getTrainingTypeName().toString(), t.getId()));
        }
        GetTrainingTypesDTO dto = new GetTrainingTypesDTO(basicTrainingTypeDTOs);
        ResponseEntity<GetTrainingTypesDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
        log.info("Training types retrieved successfully. Response details: {}", responseEntity);
        trainingTypeMetrics.incrementGetAllCounter();
        return responseEntity;
    }
}
