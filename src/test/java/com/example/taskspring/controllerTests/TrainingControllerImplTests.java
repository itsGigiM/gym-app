package com.example.taskspring.controllerTests;

import com.example.taskspring.actuator.metric.TrainingMetrics;
import com.example.taskspring.controller.TrainingControllerImpl;
import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerImplTests {
    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TrainingMetrics trainingMetrics;

    @InjectMocks
    private TrainingControllerImpl controller;

    @BeforeEach
    public void setUp() {
        controller = new TrainingControllerImpl(trainingService, trainerService, traineeService, authenticationService,
                trainingMetrics);
    }

    @Test
    public void createTraining() throws AuthenticationException {
        PostTrainingRequest request = new PostTrainingRequest("trainerUsername", "traineeUsername",
                "Training Name", LocalDate.now(), Duration.ofHours(1));

        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();

        when(trainerService.selectTrainer(request.getTrainerUsername())).thenReturn(trainer);
        when(traineeService.selectTrainee(request.getTraineeUsername())).thenReturn(trainee);
        doNothing().when(authenticationService).authenticate(anyString(), anyString());

        ResponseEntity<HttpStatus> response = controller.create(request, "admin", "admin");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }



}
