package com.example.taskspring.controllerTests;


import com.example.taskspring.controller.TrainingTypeController;
import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.example.taskspring.model.*;
import com.example.taskspring.service.TrainingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TrainingTypeController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TrainingControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingServiceImpl trainingService;

    @Autowired
    private ObjectMapper om;

    @Test
    public void getAllRetrievesAllTypes() throws Exception {
        TrainingType trainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        Trainer trainer = new Trainer("g", "m", "u", "p", true, trainingType);
        Trainee trainee = new Trainee("g", "m", "u", "p", true, "t", LocalDate.of(2000, 1, 1));
        Training t = new Training(trainee, trainer, "boxing", trainingType,
                LocalDate.of(2000, 1, 1), Duration.ofHours(1));

        when(trainingService.createTraining(any(), any(), any(), any(), any(), any())).
                thenReturn(t);
        PostTrainingRequest postTrainingRequest = new PostTrainingRequest(t.getTrainee().getUsername(), t.getTrainer().getUsername(),
                t.getTrainingName(), t.getTrainingDate(), t.getDuration());

        ResultActions response = mockMvc.perform(get("/training-types")
                .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(postTrainingRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
