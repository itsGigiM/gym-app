package com.example.taskspring.steps;

import com.example.taskspring.MessageListener;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.loginDTO.TokenDTO;
import com.example.taskspring.dto.trainerDTO.WorkHoursRetrieveDTO;
import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.springframework.http.*;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class IntegrationSteps {
    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MTk0OTE5NTAsImV4cCI6MTg3NzI1ODM1MCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.KrLUiOUV76mHpr76IAmb8NTj2hQ_v2qJFRLHSbshEG4";

    MockMvc mockMvc;
    WebApplicationContext webApplicationContext;
    RestTemplate restTemplate;


    @When("I send a message to the microservice")
    public void iSendAMessageToTheMicroservice() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        PostTrainingRequest request = new PostTrainingRequest("admin", "admin1", "t",
                LocalDate.of(2002, 1, 1), Duration.ofHours(2));
        String json = om.writeValueAsString(request);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8081/trainings", entity, String.class);
        System.out.println(response + " This is the code");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Then("the microservice should receive the message and trainer's data needs to be updated")
    public void theMicroserviceShouldReceiveTheMessageAndTrainerSDataNeedsToBeUpdated() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String url = "http://localhost:8080/work-hours/admin1";
//        try {
//            MvcResult result = mockMvc.perform(get(url)
//                            .header("Authorization", "Bearer " + TOKEN))
//                    .andExpect(status().isOk()).andReturn();
//            System.out.println("REsp " + result.getResponse().getContentAsString());
//        } catch (Exception e) {
//            fail();
//        }
    }
}
