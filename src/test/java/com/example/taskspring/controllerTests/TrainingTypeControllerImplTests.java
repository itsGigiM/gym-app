package com.example.taskspring.controllerTests;

import com.example.taskspring.actuator.metric.TrainingTypeMetrics;
import com.example.taskspring.controller.TrainingTypeControllerImpl;
import com.example.taskspring.dto.trainingTypeDTO.GetTrainingTypesDTO;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.model.TrainingTypeEnum;
import com.example.taskspring.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerImplTests {
    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeControllerImpl controller;

    @Mock
    private TrainingTypeMetrics metrics;

    @BeforeEach
    public void setUp() {
        controller = new TrainingTypeControllerImpl(trainingTypeService, metrics);
    }

    @Test
    public void getAll() {
        Set<TrainingType> trainingTypes = new HashSet<>();
        trainingTypes.add(new TrainingType(1L, TrainingTypeEnum.BOXING));
        trainingTypes.add(new TrainingType(2L, TrainingTypeEnum.KARATE));

        when(trainingTypeService.getAll()).thenReturn(trainingTypes);
        ResponseEntity<GetTrainingTypesDTO> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
