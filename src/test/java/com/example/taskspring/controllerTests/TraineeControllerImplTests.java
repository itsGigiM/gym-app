package com.example.taskspring.controllerTests;

import com.example.taskspring.actuator.metric.TraineeMetrics;
import com.example.taskspring.controller.TraineeControllerImpl;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.dto.trainerDTO.GetUnassignedTrainersDTO;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeControllerImplTests {
    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeMetrics traineeMetrics;

    @InjectMocks
    private TraineeControllerImpl traineeController;

    @BeforeEach
    public void setUp() {
        traineeController = new TraineeControllerImpl(traineeService, trainerService, traineeMetrics);
    }

    @Test
    public void createTrainee() {
        Trainee t = new Trainee("g", "m", "g.m", "pass",
                true, "t", LocalDate.of(2003, 1, 1));

        PostTraineeRequestDTO dto = new PostTraineeRequestDTO("g", "m", "t",
                LocalDate.of(2000, 1, 1));

        when(traineeService.createTrainee("g", "m", true, "t",
                LocalDate.of(2000, 1, 1))).thenReturn(t);

        AuthenticationDTO authenticationDTO = new AuthenticationDTO(t.getUsername(), t.getPassword());
        assertEquals(traineeController.create(dto), new ResponseEntity<>(authenticationDTO, HttpStatus.CREATED));
    }

    @Test
    public void retrieveTrainee(){

        Trainee t = new Trainee("g", "m", "g.m", "pass",
                true, "t", LocalDate.of(2003, 1, 1));

        when(traineeService.selectTrainee(anyString())).thenReturn(t);

        GetTraineeResponseDTO dto1 = new GetTraineeResponseDTO(t.getFirstName(), t.getLastName(), t.isActive(),
                t.getAddress(), t.getDateOfBirth(), new HashSet<>());

        assertEquals(traineeController.get("g.m"),
                new ResponseEntity<>(dto1, HttpStatus.OK));
    }

    @Test
    public void modifyTrainee(){

        Trainee t = new Trainee("g", "m", "g.m", "pass",
                true, "t", LocalDate.of(2003, 1, 1));

        when(traineeService.selectTrainee(anyString())).thenReturn(t);

        PutTraineeRequestDTO dto1 = new PutTraineeRequestDTO(t.getFirstName(), "epam", t.isActive(),
                t.getAddress(), t.getDateOfBirth());

        PutTraineeResponseDTO dto2 = new PutTraineeResponseDTO(t.getUsername(), t.getFirstName(),
                "epam", t.isActive(), t.getAddress(), t.getDateOfBirth(), new HashSet<>());

        t.setLastName("epam");
        when(traineeService.updateTrainee(any(), any())).thenReturn(t);

        assertEquals(traineeController.put("g.m", dto1),
                new ResponseEntity<>(dto2, HttpStatus.OK));
    }

    @Test
    public void testDelete(){
        String traineeUsername = "traineeUsername";

        ResponseEntity<HttpStatus> response = traineeController.delete(traineeUsername);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void updateTrainerList(){
        UpdateTraineeTrainerListRequestDTO requestDTO = new UpdateTraineeTrainerListRequestDTO("traineeUsername", Set.of("trainer1", "trainer2"));
        Trainee trainee = new Trainee();
        Trainer trainer1 = new Trainer("g", "m", "u", "p", true , new TrainingType());
        Trainer trainer2 = new Trainer();

        when(traineeService.selectTrainee(requestDTO.getUsername())).thenReturn(trainee);
        when(trainerService.selectTrainer("trainer1")).thenReturn(trainer1);
        when(trainerService.selectTrainer("trainer2")).thenReturn(trainer2);

        ResponseEntity<UpdateTraineeTrainerListResponseDTO> response = traineeController.updateTrainerList(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, trainee.getTrainers().size());
    }

    @Test
    public void getTrainingList(){
        GetTraineeTrainingListRequestDTO requestDTO = new GetTraineeTrainingListRequestDTO("traineeUsername", LocalDate.now(), LocalDate.now(), "trainerName", new TrainingType());
        Set<Training> trainings = new HashSet<>();
        when(traineeService.getTraineeTrainingList(anyString(), any(LocalDate.class), any(LocalDate.class), anyString(), any()))
                .thenReturn(trainings);

        ResponseEntity<GetUserTrainingListResponseDTO> response = traineeController.getTrainingList(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateIsActive(){
        PatchUserActiveStatusRequestDTO requestDTO = new PatchUserActiveStatusRequestDTO("traineeUsername", true);
        Trainee trainee = new Trainee();
        when(traineeService.selectTrainee(requestDTO.getUsername())).thenReturn(trainee);

        ResponseEntity<HttpStatus> response = traineeController.updateIsActive(requestDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getUnassignedTrainers(){
        String traineeUsername = "traineeUsername";
        Set<Trainer> trainers = new HashSet<>();
        trainers.add(new Trainer("g", "m", "u", "p", true , new TrainingType()));

        when(traineeService.getUnassignedTrainers(traineeUsername)).thenReturn(trainers);
        ResponseEntity<GetUnassignedTrainersDTO> response = traineeController.getUnassignedTrainers(traineeUsername);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
