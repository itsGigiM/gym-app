package com.example.taskspring.steps;

import com.example.taskspring.controller.TraineeController;
import com.example.taskspring.dto.traineeDTO.PostTraineeRequestDTO;
import com.example.taskspring.dto.traineeDTO.PutTraineeRequestDTO;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TraineeSteps {

    TraineesRepository traineesRepository;
    TraineeController traineeController;
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Autowired
    public TraineeSteps(TraineesRepository traineesRepository, TraineeController traineeController, MockMvc mockMvc) {
        this.traineesRepository = traineesRepository;
        this.traineeController = traineeController;
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

    }

    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MTk0OTE5NTAsImV4cCI6MTg3NzI1ODM1MCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.KrLUiOUV76mHpr76IAmb8NTj2hQ_v2qJFRLHSbshEG4";


    @Given("a trainee with username {string} does not exists in the system")
    public void aTraineeWithUsernameDoesNotExistsInTheSystem(String arg0) {
        traineesRepository.deleteAll();
    }

    @When("the trainee with name {string} and surname {string} is created")
    public void theTraineeWithNameAndSurnameIsCreated(String arg0, String arg1) throws JsonProcessingException {
        String url = "http://localhost:8081/trainees";
        PostTraineeRequestDTO request = new PostTraineeRequestDTO(arg0, arg1, "address", LocalDate.of(2024, 1, 1));
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();

        try {
            String json = objectMapper.writeValueAsString(request);

            mockMvc.perform(post(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated());


        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Then("the trainee's name {string} should be retrievable with username {string}")
    public void theTraineeShouldBeRetrievableWithUsername(String name, String username) {
        try {
            assertEquals(name, Objects.requireNonNull(traineeController.get(username).getBody())
                    .getFirstName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("the trainee with name {string} and surname {string} does exist in the system")
    public void theTraineeWithNameAndSurnameDoesExistInTheSystem(String arg0, String arg1) throws JsonProcessingException {
        theTraineeWithNameAndSurnameIsCreated(arg0, arg1);
    }

    @When("the trainee's name is updated to {string}")
    public void theTraineeSUsernameIsUpdatedTo(String arg0) {
        String url = "http://localhost:8081/trainees/John.Doe";
        PutTraineeRequestDTO request = new PutTraineeRequestDTO(arg0, "Doe", true, "Tb", LocalDate.of(2024, 1 ,1 ));
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
        try {
            String json = objectMapper.writeValueAsString(request);
            mockMvc.perform(put(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @When("the trainee with username {string} is deleted")
    public void theTraineeWithUsernameIsDeleted(String arg0) {
        String url = "http://localhost:8081/trainees/" + arg0;
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
        try {
            mockMvc.perform(delete(url)
                            .header("Authorization", "Bearer " + TOKEN))
                    .andExpect(status().isNoContent());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Then("the trainee should not be retrievable with username {string}")
    public void theTraineeShouldNotBeRetrievableWithUsername(String arg0) {
        String url = "http://localhost:8081/trainees/" + arg0;
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
        try {
            mockMvc.perform(get(url)
                            .header("Authorization", "Bearer " + TOKEN))
                    .andExpect(status().isNotFound());
            fail();
        }catch (Exception ignored) {
        }
    }

    @Then("an error should be thrown when the trainee {string} name is updated to {string}")
    public void anErrorShouldBeThrownWhenTheTraineeNameIsUpdatedTo(String arg0, String arg1) {
        String url = "http://localhost:8081/trainees/John.Doe";
        PutTraineeRequestDTO request = new PutTraineeRequestDTO(arg1, "Doe", true, "Tb", LocalDate.of(2024, 1 ,1 ));
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
        try {
            String json = objectMapper.writeValueAsString(request);
            mockMvc.perform(put(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isNotFound());
            fail();
        } catch (Exception ignored) {
        }
    }

    @Then("an error should be thrown if delete of the trainee {string} is requested")
    public void anErrorShouldBeThrownIfDeleteOfTheTraineeIsRequested(String arg0) {
        String url = "http://localhost:8081/trainees/" + arg0;
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
        try {
            mockMvc.perform(delete(url)
                            .header("Authorization", "Bearer " + TOKEN))
                    .andExpect(status().isNoContent());
            fail();
        } catch (Exception ignored) {
        }
    }
}
