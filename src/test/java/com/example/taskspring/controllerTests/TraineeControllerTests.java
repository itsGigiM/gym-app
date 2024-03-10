package com.example.taskspring.controllerTests;

import com.example.taskspring.controller.TraineeController;
import com.example.taskspring.dto.traineeDTO.PostTraineeRequestDTO;
import com.example.taskspring.service.TraineeServiceImpl;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TraineeController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TraineeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeServiceImpl traineeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createTrainee() throws Exception {
        PostTraineeRequestDTO request = new PostTraineeRequestDTO("g", "m", "t",
                LocalDate.of(2000, 1, 1));

        given(traineeService.createTrainee("g", "m", true, "t",
                LocalDate.of(2000, 1, 1))).
                willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/trainees")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
