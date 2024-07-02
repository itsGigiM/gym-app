package com.example.taskspring.steps;

import com.example.taskspring.controller.*;
import com.example.taskspring.dto.traineeDTO.PostTraineeRequestDTO;
import com.example.taskspring.dto.traineeDTO.PutTraineeRequestDTO;
import com.example.taskspring.dto.trainerDTO.GetTrainerTrainingListRequestDTO;
import com.example.taskspring.dto.trainerDTO.PostTrainerRequestDTO;
import com.example.taskspring.dto.trainerDTO.PutTrainerRequestDTO;
import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.TrainingsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class TrainerSteps {

    TrainerController trainerController;
    TraineeController traineeController;
    TrainersRepository trainersRepository;
    TrainingsRepository trainingsRepository;
    TrainingController trainingController;
    ObjectMapper objectMapper;

    @Mock
    HttpServletRequest request;

    MockMvc mockMvc;
    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MTk0OTE5NTAsImV4cCI6MTg3NzI1ODM1MCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.KrLUiOUV76mHpr76IAmb8NTj2hQ_v2qJFRLHSbshEG4";

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Given("a trainer with username {string} does not exists in the system")
    public void aTrainerWithUsernameDoesNotExistsInTheSystem(String arg0) {
        trainersRepository.deleteAll();
    }

    @When("the trainer with name {string} and surname {string} is created")
    public void theTrainerWithNameAndSurnameIsCreated(String arg0, String arg1) {
        String url = "http://localhost:8081/trainers";
        PostTrainerRequestDTO request = new PostTrainerRequestDTO(arg0, arg1, 1L);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();

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

    @Then("the trainer's name {string} should be retrievable with username {string}")
    public void theTrainerShouldBeRetrievableWithUsername(String name, String username) {
        try {
            assertEquals(name, Objects.requireNonNull(trainerController.get(username).getBody())
                    .getFirstName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("the trainer with name {string} and surname {string} does exist in the system")
    public void theTrainerWithNameAndSurnameDoesExistInTheSystem(String arg0, String arg1) {
        theTrainerWithNameAndSurnameIsCreated(arg0, arg1);
    }

    @When("the trainer's name is updated to {string}")
    public void theTrainerSUsernameIsUpdatedTo(String arg0) {
        String url = "http://localhost:8081/trainers/John.Doe";
        PutTrainerRequestDTO request = new PutTrainerRequestDTO(arg0, "Doe", true, 1L);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
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

    @Then("the trainer should not be retrievable with username {string}")
    public void theTrainerShouldNotBeRetrievableWithUsername(String arg0) {
        assertThrows(EntityNotFoundException.class, () -> trainerController.get(arg0));
    }

    @Then("an error should be thrown when the trainer {string} name is updated to {string}")
    public void anErrorShouldBeThrownWhenTheTrainerNameIsUpdatedTo(String arg0, String arg1) {
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
            fail();
        } catch (Exception ignored) {
        }
    }

    @Given("a trainer with username {string} does not have any trainings")
    public void aTrainerWithUsernameDoesNotHaveAnyTrainings(String arg0) {
        trainerController.create(new PostTrainerRequestDTO(arg0, "test", 1L));
    }

    @Then("trainer {string} training list must be empty")
    public void trainerTrainingListMustBeEmpty(String arg0) {
        GetTrainerTrainingListRequestDTO request = new GetTrainerTrainingListRequestDTO(arg0, LocalDate.of(2000, 1, 1),
                LocalDate.of(2024, 1, 1), "trainer");
        String url = "http://localhost:8081/trainers/training-list";
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
        try {
            String json = objectMapper.writeValueAsString(request);
            mockMvc.perform(get(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(0));
        }catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @When("a trainer with username {string} creates new training")
    public void aTrainerWithUsernameCreatesNewTraining(String arg0) {
        traineeController.create(new PostTraineeRequestDTO("Jack", "Doe", "T", LocalDate.of(2000, 1, 1)));
        String url = "http://localhost:8081/trainings";
        PostTrainingRequest request = new PostTrainingRequest("Jack.Doe", arg0, "t",
                LocalDate.of(2024, 1, 1), Duration.ofHours(1));
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
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

    @Then("training creation with the trainer {string} have to throw the error")
    public void trainingCreationWithTheTrainerHaveToThrowTheError(String arg0) {
        PostTrainingRequest request1 = new PostTrainingRequest(arg0, arg0, "t", LocalDate.of(2000, 1, 1), Duration.ofHours(1));
        String url = "http://localhost:8081/trainings";
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
        try {
            String json = objectMapper.writeValueAsString(request1);
            mockMvc.perform(post(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated());

            fail();

        } catch (Exception ignored) {
        }
    }

    @Then("the training should be in the system")
    public void theTrainingShouldBeInTheSystem() {
        try {
            assertEquals("t", trainingsRepository.findByTrainingName("t").getTrainingName());
        } catch (Exception e) {
            fail();
        }
    }
}
