package com.example.taskspring.controllerTests;


import com.example.taskspring.controller.TrainingTypeController;
import com.example.taskspring.dto.traineeDTO.PostTraineeRequestDTO;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.service.TraineeServiceImpl;
import com.example.taskspring.service.TrainingTypeServiceImpl;
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

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TrainingTypeController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingTypeServiceImpl trainingTypeService;

    @Test
    public void getAllRetrievesAllTypes() throws Exception {

        Set<TrainingType> s = new HashSet<>();
        when(trainingTypeService.getAll()).
                thenReturn(s);

        ResultActions response = mockMvc.perform(get("/training-types")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

}
